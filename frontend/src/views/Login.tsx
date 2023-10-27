import { Layout, Button } from "antd";
import { Header } from "antd/es/layout/layout";
import { Link } from "react-router-dom";

function Login() {
  return (
    <Layout>
      <Header style={{ borderBottom: 0, borderTop: "2px", backgroundColor: "white", display: "flex", alignItems: "center", justifyContent: "end" }}>
        <Link to="/">
            <Button type="primary">Natrag</Button>
        </Link>
      </Header>
      <Layout>

      </Layout>
    </Layout>
  );
}

export default Login;
