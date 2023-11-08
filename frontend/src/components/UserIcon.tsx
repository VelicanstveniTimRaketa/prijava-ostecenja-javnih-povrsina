import { useContext } from "react";
import { Typography } from "antd";
import { UserOutlined, LogoutOutlined, ProfileOutlined } from "@ant-design/icons";
import Avatar from "antd/es/avatar/avatar";
import Layout from "antd/es/layout/layout";
import List from "antd/es/list";
import { StateContext } from "../utils/state";
import { useNavigate } from "react-router";
import { useToggleable } from "../hooks/useToggleable";

function UserIcon() {
  const { global, setGlobal } = useContext(StateContext);
  const [active, toggle] = useToggleable(false);
  const navigate = useNavigate();

  function onLogout() {
    setGlobal({ ...global, user: undefined });
    navigate("/login");
  }

  const itemStyle = { justifyContent: "center", gap: "1em", cursor: "pointer" };

  return (
    <div style={{ position: "relative", display: "flex", alignItems: "center" }}>
      <Typography.Title level={5} style={{ margin: "0 1em" }}>{global.user?.username}</Typography.Title>
      <Avatar onClick={toggle} icon={<UserOutlined />} />
      {active && (
        <Layout
          className="shadow"
          style={{
            position: "absolute",
            zIndex: 1,
            right: 0,
            top: "100%",
            width: "15em",
            marginTop: "1em",
          }}
        >
          <List style={{ margin: "0 0.5em", textAlign: "right" }}>
            <List.Item onClick={() => navigate("/profile")} style={itemStyle}>
              <UserOutlined />
              <Typography>Moj profil</Typography>
            </List.Item>
            <List.Item onClick={() => navigate("/profile/reports")} style={itemStyle}>
              <ProfileOutlined />
              <Typography>Pregled mojih prijava</Typography>
            </List.Item>
            <List.Item onClick={onLogout} style={itemStyle}>
              <LogoutOutlined />
              <Typography>Odjava</Typography>
            </List.Item>
          </List>
        </Layout>
      )}
    </div>
  );
}

export default UserIcon;
