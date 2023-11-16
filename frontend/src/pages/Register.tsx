import { Layout, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import { useContext, useEffect } from "react";
import { StateContext } from "../utils/state";
import { UserRegiser } from "../utils/types";
import { register } from "../utils/fetch";
import LoginRegisterHeader from "../components/LoginRegisterHeader";
import UserForm from "../components/UserForm";

function Register() {
  const { global, setGlobal } = useContext(StateContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (global.user) navigate("/");
  }, [global.user, navigate]);

  if (global.user) return <div></div>;

  function onRegister(data: UserRegiser) {
    register(data).then(() => {
      setGlobal({...global, user: { ...data, id: 1, role: "USER" }});
      navigate("/");
    });
  }

  return (
    <Layout>
      <LoginRegisterHeader />
      <Content style={{ display: "flex", alignItems: "center", flexDirection: "column", flex: "1", width: "100%" }}>
        <Typography.Title level={2}>Registracija</Typography.Title>
        <UserForm onSubmit={onRegister} />
      </Content>
    </Layout>
  );
}

export default Register;
