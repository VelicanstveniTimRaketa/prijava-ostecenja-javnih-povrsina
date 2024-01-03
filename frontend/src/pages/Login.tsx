import { useContext, useEffect, useState } from "react";
import { Layout, Button, Typography, Form, Input, notification } from "antd";
import { LockOutlined, MailOutlined } from "@ant-design/icons";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import { useForm } from "antd/es/form/Form";
import { StateContext } from "../utils/state";
import { Link } from "react-router-dom";
import { login } from "../utils/fetch";
import { LoginData, Response } from "../utils/types";
import LoginRegisterHeader from "../components/LoginRegisterHeader";
import Check from "../components/Check";

function Login() {
  const { global, setGlobal } = useContext(StateContext);
  const [form] = useForm();
  const [response, setResponse] = useState<Response<LoginData>>();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (!response) return;

    if (!response.success) {
      notification.error({
        message: response.errors && response.errors[0],
        placement: "top",
      });
      return;
    }
    if (!response.data) {
      console.error("no response data");
      return;
    }
    console.log(response.data.korisnik);
    setGlobal({ ...global, user: { ...response.data.korisnik, token: response.data.token } });
    navigate("/");
  }, [global, setGlobal, navigate, response]);

  async function onSubmit() {
    setLoading(true);
    const data = { email: form.getFieldValue("email"), password: form.getFieldValue("password") };
    login(data).then(v => {
      setResponse(v);
      setLoading(false);
    });
  }

  return (
    <Check if={!global.user} elseNavigateTo="/">
      <LoginRegisterHeader />
      <Layout>
        <Content style={{ display: "flex", alignItems: "center", flexDirection: "column", flex: "1", width: "100%" }}>
          <Typography.Title level={2}>Prijava</Typography.Title>
          <Form
            id="login"
            form={form}
            onFinish={onSubmit}
            style={{ width: "100%", maxWidth: "20em" }}
          >
            <Form.Item
              name="email"
              rules={[{ required: true, type: "email", message: "Molimo unesite važeći email" }]}
            >
              <Input prefix={<MailOutlined style={{ color: "rgba(0, 0, 0, 0.25)" }} />} placeholder="Email" autoFocus />
            </Form.Item>
            <Form.Item
              name="password"
              rules={[
                { required: true, message: "Molimo unesite svoju lozinku" },
                { min: 8, message: "Lozinka mora imati barem 8 znakova" }
              ]}
            >
              <Input.Password placeholder="Lozinka" prefix={<LockOutlined style={{ color: "rgba(0, 0, 0, 0.25)" }} />} />
            </Form.Item>
            <Form.Item key="submit">
              <Button type="primary" loading={loading} htmlType="submit" style={{ width: "100%" }}>Prijava</Button>
              Nemaš račun? <Link to="/register">Registriraj se!</Link>
            </Form.Item>
          </Form>
        </Content>
      </Layout>
    </Check>
  );
}

export default Login;
