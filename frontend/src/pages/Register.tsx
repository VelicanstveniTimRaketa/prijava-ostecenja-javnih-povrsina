import { Layout, Typography, notification } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { StateContext } from "../utils/state";
import { LoginData, Response, UserRegiser } from "../utils/types";
import { register } from "../utils/fetch";
import { getUser } from "../utils/user";
import LoginRegisterHeader from "../components/LoginRegisterHeader";
import UserForm from "../components/UserForm";
import Check from "../components/Check";

function Register() {
  const { global, setGlobal } = useContext(StateContext);
  const [response, setResponse] = useState<Response<LoginData>>();
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
    setGlobal({ ...global, user: getUser(response.data.korisnik, response.data.token, response.data.refreshToken) });
    navigate("/");
  }, [global, setGlobal, navigate, response]);

  async function onRegister(data: UserRegiser) {
    setResponse(await register(data));
  }

  return (
    <Check if={!global.user} elseNavigateTo="/">
      <Layout>
        <LoginRegisterHeader />
        <Content style={{ display: "flex", alignItems: "center", flexDirection: "column", flex: "1", width: "100%" }}>
          <Typography.Title level={2}>Registracija</Typography.Title>
          <UserForm onSubmit={onRegister} />
        </Content>
      </Layout>
    </Check>
  );
}

export default Register;
