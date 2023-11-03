import { useEffect, useState } from "react";
import { Layout, Button, Typography, Form, Input, Upload, message } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import { useForm } from "antd/es/form/Form";
import { StoreValue } from "antd/es/form/interface";
import { RuleObject } from "antd/es/form";
import { PlusOutlined } from "@ant-design/icons";
import { getBase64 } from "../utils/imageTransform";
import LoginRegisterHeader from "../components/LoginRegisterHeader";
import type { RcFile } from "antd/es/upload/interface";

function Register() {
  const [form] = useForm();
  const [image, setImage] = useState<RcFile>();
  const [imageB64, setImageB64] = useState<string>();
  const navigate = useNavigate();

  useEffect(() => {
    image && getBase64(image).then(setImageB64);
  }, [image]);

  function onSubmit() {
    navigate("/");
  }

  function passwordValidator(_: RuleObject, value: StoreValue) {
    if (!value || form.getFieldValue("password") === value) {
      return Promise.resolve();
    }
    return Promise.reject(new Error("The new password that you entered do not match!"));
  }

  const onUpload = (file: RcFile) => {
    const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
    if (!isJpgOrPng) {
      message.error("You can only upload JPG/PNG file!");
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error("Image must smaller than 2MB!");
    }
    //return isJpgOrPng && isLt2M;
    setImage(file);
    return false;
  };

  return (
    <Layout>
      <LoginRegisterHeader />
      <Layout>
        <Content style={{ display: "flex", alignItems: "center", flexDirection: "column", flex: "1", width: "100%" }}>
          <Typography.Title level={2}>Registracija</Typography.Title>
          <Form
            id="register"
            form={form}
            onFinish={onSubmit}
            labelCol={{ span: 7 }}
            wrapperCol={{ span: 20 }}
            style={{ width: "100%", maxWidth: "32em" }}
          >
            <Form.Item label="Korisničko ime: " name="username" rules={[{ required: true, message: "Molimo unesite svoje korisničko ime" }]}>
              <Input autoFocus />
            </Form.Item>
            <Form.Item label="Ime: " name="name" rules={[{ required: true, message: "Molimo unesite svoje ime" }]}>
              <Input />
            </Form.Item>
            <Form.Item label="Prezime: " name="surname" rules={[{ required: true, message: "Molimo unesite svoje prezime" }]}>
              <Input />
            </Form.Item>
            <Form.Item label="Email: " name="email" rules={[{ required: true, type: "email", message: "Molimo unesite važeći email" }]}>
              <Input />
            </Form.Item>
            <Form.Item label="Lozinka: " name="password" hasFeedback rules={[{ required: true, message: "Molimo unesite svoju lozinku" }]}>
              <Input.Password />
            </Form.Item>
            <Form.Item
              label="Ponovite lozinku: " name="confirmPassword" hasFeedback
              rules={[
                { required: true, message: "Molimo unesite potvrdu lozinke" },
                { validator: passwordValidator, message: "Lozinke se ne poklapaju" }
              ]}
            >
              <Input.Password />
            </Form.Item>
            <Form.Item label="Slika profila: " name="avatar" valuePropName="avatar" hasFeedback rules={[{ required: true, message: "Molimo učitajte sliku profila" }]}>
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
              <Button type="primary" htmlType="submit">Registriraj se</Button>
            </Form.Item>
          </Form>
        </Content>
      </Layout>
    </Layout>
  );
}

export default Register;
