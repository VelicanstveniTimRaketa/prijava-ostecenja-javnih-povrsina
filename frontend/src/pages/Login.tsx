import { Layout, Button, Typography, Form, Input } from "antd";
import { Content, Header } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import Logo from "../components/Logo";

function Login() {
  const navigate = useNavigate();
  return (
    <Layout>
      <Header
        style={{
          borderBottom: 0,
          borderTop: "2px",
          backgroundColor: "white",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between"
        }}
      >
        <Logo />
        <Button onClick={() => navigate(-1)}>Natrag</Button>
      </Header>
      <Layout>
        <Content style={{ display: "flex", alignItems: "center", flexDirection: "column", flex: "1", width: "100%" }}>
          <Typography.Title level={2}>Prijava</Typography.Title>
          <Form labelCol={{ span: 7 }} wrapperCol={{ span: 20 }} style={{ width: "100%", maxWidth: "30em" }}>
            <Form.Item
              label="Korisničko ime: "
              name="username"
              rules={[{ required: true, message: "Molimo unesite svoje korisničko ime" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Lozinka: "
              name="password"
              rules={[{ required: true, message: "Molimo unesite svoju lozinku" }]}
            >
              <Input.Password />
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 10 }}>
              <Button type="primary" htmlType="submit">Prijava</Button>
            </Form.Item>
          </Form>
        </Content>
      </Layout>
    </Layout>
  );
}

export default Login;
