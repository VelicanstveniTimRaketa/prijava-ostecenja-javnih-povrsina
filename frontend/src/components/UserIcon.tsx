import { useContext, useEffect, useRef, useState } from "react";
import { Typography } from "antd";
import { UserOutlined, LogoutOutlined, ProfileOutlined } from "@ant-design/icons";
import Avatar from "antd/es/avatar/avatar";
import Layout from "antd/es/layout/layout";
import List from "antd/es/list";
import { StateContext } from "../utils/state";
import { useNavigate } from "react-router";

function UserIcon() {
  const [showMenu, setShowMenu] = useState(false);
  const { global, setGlobal } = useContext(StateContext);
  const navigate = useNavigate();
  const componentRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (!showMenu) return;
    let flag = false;

    const destroy = () => {
      if (!flag) {
        flag = true;
        return;
      }
      setShowMenu(false);
    };
    document.addEventListener("click", destroy);
    return () => document.removeEventListener("click", destroy);
  }, [showMenu]);

  function onLogout() {
    setGlobal({ ...global, user: undefined });
    navigate("/login");
  }

  const itemStyle = { justifyContent: "center", gap: "1em", cursor: "pointer" };

  return (
    <div ref={componentRef} style={{ position: "relative", display: "flex", alignItems: "center" }}>
      <Typography.Title level={5} style={{ margin: "0 1em" }}>{global.user?.username}</Typography.Title>
      <Avatar onClick={() => setShowMenu(!showMenu)} icon={<UserOutlined />} />
      {showMenu && (
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
            <List.Item onClick={() => navigate("/userReports")} style={itemStyle}>
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
