import { Layout, Button, Typography, Form, Input } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import { useForm } from "antd/es/form/Form";
import { useContext, useEffect } from "react";
import { StateContext } from "../utils/state";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
import LoginRegisterHeader from "../components/LoginRegisterHeader";

function Login() {
  const { global, setGlobal } = useContext(StateContext);
  const [form] = useForm();
  const navigate = useNavigate();

  useEffect(() => {
    if (global.user) navigate("/");
  }, [global.user, navigate]);

  if (global.user) return <div></div>;

  function onSubmit() {
    setGlobal({
      ...global,
      user: { username: form.getFieldValue("username"), email: "mail", name: "name", surname: "surname", id: 0 }
    });
    navigate("/");
  }

  return (
    <Layout>
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
              name="username"
              rules={[{ required: true, message: "Molimo unesite svoje korisničko ime" }]}
            >
              <Input prefix={<UserOutlined style={{ color: "rgba(0, 0, 0, 0.25)" }} />} placeholder="Korisničko ime" autoFocus />
            </Form.Item>
            <Form.Item
              name="password"
              rules={[{ required: true, message: "Molimo unesite svoju lozinku" }]}
            >
              <Input.Password placeholder="Lozinka" prefix={<LockOutlined style={{ color: "rgba(0, 0, 0, 0.25)" }} />} />
            </Form.Item>
            <Form.Item key="submit">
              <Button type="primary" htmlType="submit" style={{ width: "100%" }}>Prijava</Button>
              Nemaš račun? <Link to="/register">Registriraj se!</Link>
            </Form.Item>
            <Form.Item>
          </Form.Item>
          </Form>
        </Content>
      </Layout>
    </Layout>
  );
}

export default Login;
