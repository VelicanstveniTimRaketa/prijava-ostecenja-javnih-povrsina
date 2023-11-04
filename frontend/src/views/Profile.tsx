import { useContext } from "react";
import { Button, Card, Layout, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { UserOutlined, EditOutlined } from "@ant-design/icons";
import { StateContext } from "../utils/state";
import { useNavigate } from "react-router";

function Profile() {
  const { global: { user } } = useContext(StateContext);
  const navigate = useNavigate();

  if (!user) throw TypeError();

  return (
    <Content style={{ display: "flex", margin: "2em", alignItems: "center", flexDirection: "column" }}>
      <Card style={{ display: "flex", flexDirection: "row", flex: 0 }}>
        <div style={{ display: "flex", margin: "2em", gap: "2em" }}>
          <div style={{ display: "flex", alignItems: "center", width: "10em", height: "10em", flex: 0 }}>
            {/* <Image preview={false} style={{ width: "10em", height: "10em" }} fallback="" /> */}
            <Layout
              className="shadow"
              style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                width: "10em",
                height: "10em",
                background: "#cecece",
                borderRadius: "100em"
              }}>
              <UserOutlined style={{ fontSize: "6em" }} />
            </Layout>
          </div>
          <div style={{ flexDirection: "column" }}>
            <Typography.Title level={1} style={{ marginTop: 0 }}>{user.name} {user.surname}</Typography.Title>
            <Typography.Title level={3} style={{ margin: 0 }}>{user.username}</Typography.Title>
            <Typography.Title level={3} style={{ margin: 0, fontWeight: "normal" }}>{user.email}</Typography.Title>
            <Button onClick={() => navigate("edit")} style={{ marginTop: "2em" }} icon={<EditOutlined />}>Izmijeni profil</Button>
          </div>
        </div>
      </Card>
    </Content>
  );
}

export default Profile;
