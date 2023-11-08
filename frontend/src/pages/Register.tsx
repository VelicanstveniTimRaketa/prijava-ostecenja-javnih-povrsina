import { Layout, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import LoginRegisterHeader from "../components/LoginRegisterHeader";
import UserForm from "../components/UserForm";
import { useContext, useEffect } from "react";
import { StateContext } from "../utils/state";

function Register() {
  const { global } = useContext(StateContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (global.user) navigate("/");
  }, [global.user, navigate]);

  if (global.user) return <div></div>;
  return (
    <Layout>
      <LoginRegisterHeader />
        <Content style={{ display: "flex", alignItems: "center", flexDirection: "column", flex: "1", width: "100%" }}>
          <Typography.Title level={2}>Registracija</Typography.Title>
          <UserForm onSubmit={() => navigate("/")} />
        </Content>
    </Layout>
  );
}

export default Register;
