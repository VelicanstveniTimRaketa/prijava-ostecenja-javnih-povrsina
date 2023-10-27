import { useContext, useState } from "react";
import { StateContext } from "../utils/state";
import { Button, Layout, Menu } from "antd";
import { Avatar } from "antd";
import { Header } from "antd/es/layout/layout";
import { MenuInfo } from "rc-menu/lib/interface";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router";
import { UserOutlined } from "@ant-design/icons";
import { MenuPropsWithComponent } from "../utils/types";
import { Route, Routes } from "react-router";
import Start from "../views/Start";
import Explore from "../views/Explore";
import Report from "../views/Report";

const items: MenuPropsWithComponent = [
  {
    key: "",
    label: "Početna",
    component: Start,
  },
  {
    key: "search",
    label: "Pretraga",
    component: Explore,
  },
  {
    key: "report",
    label: "Prijavi štetu",
    component: Report,
  },
];

function App() {
  const { global } = useContext(StateContext);
  const [currentPage, setPage] = useState(window.location.pathname);
  const navigate = useNavigate();

  function handleMenuClick(info: MenuInfo) {
    setPage(info.key);
    navigate(info.key);
  }

  return (
    <Layout>
      <Header style={{ borderBottom: 0, borderTop: "2px", backgroundColor: "white", display: "flex", alignItems: "center", justifyContent: "end" }}>
        <div className="demo-logo" />
        <Menu style={{ display: "flex", padding: "0 1em" }} mode="horizontal" onClick={handleMenuClick} defaultSelectedKeys={[currentPage]} items={items} />
        {global.loggedIn ? (
            <Avatar icon={<UserOutlined />} />
        ) : (
            <Link to="/login">
                <Button type="primary">Prijava</Button>
            </Link>
        )}
      </Header>
      <Routes>
        {items.map(item => (
          <Route key={item.key} path={item.key as string} element={<item.component />} />
        ))}
      </Routes>
    </Layout>
  );
}

export default App;
 