import { Layout, Typography, notification } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { StateContext } from "../utils/state";
import { LoginData, Response, UserRegiser } from "../utils/types";
import { register } from "../utils/fetch";
import LoginRegisterHeader from "../components/LoginRegisterHeader";
import UserForm from "../components/UserForm";

function Register() {
  const { global, setGlobal } = useContext(StateContext);
  const [response, setResponse] = useState<Response<LoginData>>();
  const navigate = useNavigate();

  useEffect(() => {
    if (global.user) navigate("/");
  }, [global.user, navigate]);

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
    setGlobal({ ...global, user: response.data.korisnik, token: response.data.token });
    navigate("/");
  }, [global, setGlobal, navigate, response]);

  if (global.user) return <div></div>;

  function onRegister(data: UserRegiser) {
    register(data).then(setResponse);
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
