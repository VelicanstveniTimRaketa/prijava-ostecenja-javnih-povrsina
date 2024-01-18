import { useContext, useState } from "react";
import { Button, Card, Layout, Modal, Tag, Typography, notification } from "antd";
import { Content } from "antd/es/layout/layout";
import { UserOutlined, EditOutlined } from "@ant-design/icons";
import { StateContext } from "../utils/state";
import { useNavigate } from "react-router";
import { deleteUser } from "../utils/fetch";

function Profile() {
  const { global, setGlobal } = useContext(StateContext);
  const navigate = useNavigate();
  const [deletingUser, setDeletingUser] = useState(false);

  const { user } = global;
  if (!user) return <div></div>;

  const onDelete = () => {
    deleteUser(user.id).then(r => {
      if (r.success) {
        notification.success({ message: "Profil uspješno izbrisan", placement: "top" });
        setGlobal({ ...global, user: undefined });
        localStorage.removeItem("token");
        localStorage.removeItem("refreshToken");
        navigate("/search");
      } else {
        notification.error({ message: "Pogreška pri brisanju profila", description: r.errors && r.errors[0], placement: "top" });
      }
    });
  };

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
            <Typography.Title level={1} style={{ marginTop: 0, display: "flex", alignItems: "center", gap: "0.5em" }}>
              {user.username}
              {user.role === "ADMIN" && <Tag style={{ height: "fit-content" }} color="blue">Admin</Tag>}
            </Typography.Title>
            <Typography.Title level={3} style={{ margin: 0 }}>{user.ime} {user.prezime}</Typography.Title>
            <Typography.Title level={3} style={{ margin: 0, fontWeight: "normal" }}>{user.email}</Typography.Title>
            {user.ured && user.ured_status === "active" && <div style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
              <Typography.Title level={3} style={{ margin: "0.5em 0", fontWeight: "normal" }}>{user.ured.naziv}</Typography.Title>
              <Button onClick={() => navigate("/offices/myOffice")}>Moj ured</Button>
            </div>}
            <div style={{ display: "flex", marginTop: "1em", gap: "1em" }}>
              <Button onClick={() => navigate("edit")} icon={<EditOutlined />}>Izmijeni podatke</Button>
              <Button danger onClick={() => setDeletingUser(true)} icon={<EditOutlined />}>Izbriši profil</Button>
            </div>
          </div>
        </div>
        <Modal
          open={deletingUser}
          title="Brisanje korisničkog profila"
          onOk={onDelete}
          onCancel={() => setDeletingUser(false)}
          okText="U redu"
          cancelText="Odustani"
        >
          <p>Želite li stvarno izbrisati korisnički profil?</p>
        </Modal>
      </Card>
    </Content>
  );
}

export default Profile;
