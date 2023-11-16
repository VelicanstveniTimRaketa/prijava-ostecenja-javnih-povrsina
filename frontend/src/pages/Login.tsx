import { useContext, useEffect } from "react";
import { Layout, Button, Typography, Form, Input } from "antd";
import { LockOutlined, MailOutlined } from "@ant-design/icons";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import { useForm } from "antd/es/form/Form";
import { StateContext } from "../utils/state";
import { Link } from "react-router-dom";
import { login } from "../utils/fetch";
import { hash } from "../utils/hasher";
import LoginRegisterHeader from "../components/LoginRegisterHeader";

function Login() {
  const { global, setGlobal } = useContext(StateContext);
  const [form] = useForm();
  const navigate = useNavigate();

  useEffect(() => {
    if (global.user) navigate("/");
  }, [global.user, navigate]);

  if (global.user) return <div></div>;

  async function onSubmit() {
    //navigate("/");
    const passwordHash = await hash(form.getFieldValue("password"));

    login({
      email: form.getFieldValue("email"),
      password: passwordHash,
    }).then(console.log);
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
              <Button type="primary" htmlType="submit" style={{ width: "100%" }}>Prijava</Button>
              Nemaš račun? <Link to="/register">Registriraj se!</Link>
            </Form.Item>
          </Form>
        </Content>
      </Layout>
    </Layout>
  );
}

export default Login;
