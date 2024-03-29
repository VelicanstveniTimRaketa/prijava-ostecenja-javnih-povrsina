import { useState, useEffect } from "react";
import { Form, Input, Upload, Button, message, Row } from "antd";
import { RuleObject } from "antd/es/form";
import { useForm } from "antd/es/form/Form";
import { StoreValue } from "antd/es/form/interface";
import { RcFile } from "antd/es/upload";
import { getBase64 } from "../utils/imageTransform";
import { PlusOutlined } from "@ant-design/icons";
import { User, UserRegiser } from "../utils/types";

interface UserFormProps {
  initialData?: User;
  noPassword?: boolean;
  submitText?: string;
  onSubmit: (user: UserRegiser) => void;
}

function UserForm(props: UserFormProps) {
  const [form] = useForm();
  const [image, setImage] = useState<RcFile>();
  const [imageB64, setImageB64] = useState<string>();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    image && getBase64(image).then(setImageB64);
  }, [image]);

  async function onSubmit() {
    setLoading(true);
    await props.onSubmit({
      username: form.getFieldValue("username"),
      email: form.getFieldValue("email"),
      ime: form.getFieldValue("name"),
      prezime: form.getFieldValue("surname"),
      password: form.getFieldValue("password"),
    });
    setLoading(false);
  }

  function passwordValidator(_: RuleObject, value: StoreValue) {
    if (!value || form.getFieldValue("password") === value) {
      return Promise.resolve();
    }
    return Promise.reject();
  }

  const onUpload = (file: RcFile) => {
    const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
    if (!isJpgOrPng) {
      message.error("Moguće je jedino uploadati JPG/PNG datoteke!");
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error("Slika mora biti manja od 2MB!");
    }
    //return isJpgOrPng && isLt2M;
    setImage(file);
    return false;
  };

  return (
    <Form
      id="register"
      form={form}
      initialValues={props.initialData}
      onFinish={onSubmit}
      labelCol={{ span: 7 }}
      wrapperCol={{ span: 20 }}
      style={{ width: "100%", maxWidth: "32em" }}
    >
      <Form.Item label="Korisničko ime:" name="username" rules={[{ required: true, message: "Molimo unesite svoje korisničko ime" }]}>
        <Input autoFocus />
      </Form.Item>
      <Form.Item required label="Ime i prezime:">
        <Row style={{ gap: "0.5em" }}>
          <Form.Item name="name" style={{ flex: 1, marginBottom: 0 }} rules={[{ required: true, message: "Molimo unesite svoje ime" }]}>
            <Input placeholder="Ime" />
          </Form.Item>
          <Form.Item name="surname" style={{ flex: 1, marginBottom: 0 }} rules={[{ required: true, message: "Molimo unesite svoje prezime" }]}>
            <Input placeholder="Prezime" />
          </Form.Item>
        </Row>
      </Form.Item>
      <Form.Item label="Email:" name="email" rules={[{ required: true, type: "email", message: "Molimo unesite važeći email" }]}>
        <Input />
      </Form.Item>
      {!props.noPassword && <>
        <Form.Item label="Lozinka:" name="password" hasFeedback rules={[
          { required: true, message: "Molimo unesite svoju lozinku" },
          { min: 8, message: "Lozinka mora imati barem 8 znakova" }
        ]}>
          <Input.Password />
        </Form.Item>
        <Form.Item
          label="Ponovite lozinku:" name="confirmPassword" hasFeedback
          rules={[
            { required: true, message: "Molimo unesite potvrdu lozinke" },
            { validator: passwordValidator, message: "Lozinke se ne poklapaju" }
          ]}
        >
          <Input.Password />
        </Form.Item>
      </>}
      <Form.Item label="Slika profila:" name="avatar" valuePropName="avatar" hasFeedback rules={[{ message: "Molimo učitajte sliku profila" }]}>
        <Upload
          name="avatar"
          listType="picture-circle"
          showUploadList={false}
          beforeUpload={onUpload}
        >
          {image ? <img src={imageB64} alt="avatar" style={{ width: "100%" }} /> : (
            <div>
              <PlusOutlined />
              <div style={{ marginTop: 8 }}>Učitaj</div>
            </div>
          )}
        </Upload>
      </Form.Item>
      <Form.Item key="submit" wrapperCol={{ offset: 7 }}>
        <Button type="primary" loading={loading} htmlType="submit">{props.submitText ?? "Registriraj se"}</Button>
      </Form.Item>
    </Form>
  );

}

export default UserForm;