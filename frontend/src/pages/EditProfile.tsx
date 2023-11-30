import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { Layout, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { StateContext } from "../utils/state";
import UserForm from "../components/UserForm";

function EditProfile() {
  const { global: { user } } = useContext(StateContext);
  const navigate = useNavigate();

  if (!user) throw TypeError();

  return (
    <Layout>
      <Content style={{ display: "flex", alignItems: "center", flexDirection: "column", flex: "1", width: "100%" }}>
        <Typography.Title level={2}>Promijeni podatke profila</Typography.Title>
        <UserForm initialData={user} noPassword onSubmit={() => navigate("/")} />
      </Content>
    </Layout>
  );
}

export default EditProfile;
