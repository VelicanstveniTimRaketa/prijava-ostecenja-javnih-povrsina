import { useContext } from "react";
import { Typography } from "antd";
import { UserOutlined, LogoutOutlined, ProfileOutlined } from "@ant-design/icons";
import { StateContext } from "../utils/state";
import { useNavigate } from "react-router";
import { useToggleable } from "../hooks/useToggleable";
import Avatar from "antd/es/avatar/avatar";
import FloatingList from "./FloatingList";
import { clearSavedUser } from "../utils/user";

function UserIcon() {
  const { global, setGlobal } = useContext(StateContext);
  const [active, toggle] = useToggleable(false);
  const navigate = useNavigate();

  function onLogout() {
    setGlobal({ ...global, user: undefined });
    clearSavedUser();
    navigate("/login");
  }

  const items = [
    {
      text: "Moj profil",
      icon: <UserOutlined />,
      onClick: () => navigate("/profile"),
    },
    {
      text: "Pregled mojih prijava",
      icon: <ProfileOutlined />,
      onClick: () => navigate("/profile/reports"),
    },
    {
      text: "Odjava",
      icon: <LogoutOutlined />,
      onClick: onLogout,
    }
  ];

  return (
    <div style={{ position: "relative", display: "flex", alignItems: "center" }}>
      <Typography.Title level={5} style={{ margin: "0 1em" }}>{global.user?.username}</Typography.Title>
      <Avatar onClick={toggle} className="onClick" style={{ cursor: "pointer" }} icon={<UserOutlined />} />
      {active && <FloatingList data={items} />}
    </div>
  );
}

export default UserIcon;
