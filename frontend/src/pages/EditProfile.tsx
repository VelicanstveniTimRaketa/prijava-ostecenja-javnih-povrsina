import { Layout, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import LoginRegisterHeader from "../components/LoginRegisterHeader";
import UserForm from "../components/UserForm";

function EditProfile() {
  const navigate = useNavigate();
  return (
    <Layout>
      <LoginRegisterHeader />
        <Content style={{ display: "flex", alignItems: "center", flexDirection: "column", flex: "1", width: "100%" }}>
          <Typography.Title level={2}>Promijeni podatke profila</Typography.Title>
          <UserForm onSubmit={() => navigate("/")} />
        </Content>
    </Layout>
  );
}

export default EditProfile;
