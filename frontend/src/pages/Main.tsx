import { useContext, useState } from "react";
import { StateContext } from "../utils/state";
import { Button, Layout, Menu } from "antd";
import { Header } from "antd/es/layout/layout";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router";
import { PlusOutlined } from "@ant-design/icons";
import { MenuPropsWithComponent } from "../utils/types";
import { Route, Routes } from "react-router";
import Start from "../views/Start";
import Explore from "../views/Explore";
import Report from "../views/Report";
import Logo from "../components/Logo";
import UserIcon from "../components/UserIcon";

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
  const [currentPage, setPage] = useState(window.location.pathname.split("/")[1]);
  const navigate = useNavigate();

  function navigateToPage(page: string) {
    setPage(page);
    navigate(page);
  }

  return (
    <Layout>
      <Header
        style={{
          borderBottom: 0,
          borderTop: "2px",
          backgroundColor: "white",
          display: "flex",
          alignItems: "center",
          justifyContent: "end",
          background: "white",
        }}
      >
        <Layout style={{ display: "flex", flexDirection: "row", background: "white", alignItems: "center" }}>
          <Logo />
          <Menu
            style={{ display: "flex", padding: "0 1em" }}
            mode="horizontal"
            onClick={info => navigateToPage(info.key)}
            selectedKeys={[currentPage]}
            items={items}
          />
          <Button type="primary" size="large" shape="round" icon={<PlusOutlined />} onClick={() => navigateToPage("report")}>
            Prijavi štetu
          </Button>
        </Layout>
        {global.user ? (
          <UserIcon />
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
