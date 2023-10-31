import { useContext, useEffect, useRef, useState } from "react";
import { Typography } from "antd";
import { UserOutlined, LogoutOutlined } from "@ant-design/icons";
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
    if (showMenu) {
      const destroy = (e: MouseEvent) => {
        if (!componentRef.current?.contains(e.target as Node)) {
          setShowMenu(false);
        }
      };
      document.addEventListener("click", destroy);
      return () => document.removeEventListener("click", destroy);
    }
  }, [showMenu]);

  function onLogout() {
    setGlobal({ ...global, user: undefined });
    navigate("/login");
  }

  return (
    <div ref={componentRef} style={{ position: "relative", display: "flex", alignItems: "center" }}>
      <Typography.Title level={5} style={{ margin: "0 1em" }}>{global.user?.username}</Typography.Title>
      <Avatar onClick={() => setShowMenu(!showMenu)} icon={<UserOutlined />} />
      {showMenu && (
        <Layout
          style={{
            position: "absolute",
            zIndex: 1,
            right: 0,
            width: "10em",
            boxShadow: "0 0 5px 1px rgba(30, 30, 30, 0.33)",
            transform: "translateY(100%)"
          }}
        >
          <List style={{ margin: "0.5em", textAlign: "right" }}>
            <List.Item onClick={onLogout} style={{ justifyContent: "center", gap: "1em", cursor: "pointer" }}>
              <LogoutOutlined />
              <Typography>Logout</Typography>
            </List.Item>
          </List>
        </Layout>
      )}
    </div>
  );
}

export default UserIcon;
