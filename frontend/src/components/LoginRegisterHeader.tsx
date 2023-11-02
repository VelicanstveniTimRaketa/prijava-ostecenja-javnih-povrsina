import { Button } from "antd";
import { Header } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import Logo from "./Logo";

function LoginRegisterHeader() {
  const navigate = useNavigate();

  return (
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
  );
}

export default LoginRegisterHeader;
