import { useContext } from "react";
import { StateContext } from "../utils/state";
import { Button, Layout, Menu } from "antd";
import { Header } from "antd/es/layout/layout";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router";
import { PlusOutlined } from "@ant-design/icons";
import { MenuPropsWithComponent } from "../utils/types";
import { Route, Routes } from "react-router";
import WelcomeView from "../views/WelcomeView";
import Explore from "../views/Explore";
import NewReport from "../views/NewReport";
import Report from "../pages/Report";
import Logo from "../components/Logo";
import UserIcon from "../components/UserIcon";
import ProfileRoutes from "../components/ProfileRoutes";
import GradskiUredi from "../views/GradskiUredi";

const items: MenuPropsWithComponent = [
  {
    key: "",
    label: "Početna",
    component: WelcomeView,
  },
  {
    key: "search",
    label: "Pretraga",
    component: Explore,
  },
  {
    key: "offices",
    label: "Gradski uredi",
    component: GradskiUredi,
  },
];

function App() {
  const { global } = useContext(StateContext);
  const navigate = useNavigate();
  const currentPage = window.location.pathname.split("/")[1];

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
            style={{ display: "flex", padding: "0 1em", minWidth: "fit-content" }}
            mode="horizontal"
            onClick={info => navigate(info.key)}
            selectedKeys={[currentPage]}
            items={items}
          />
          <Button type="primary" size="large" shape="round" icon={<PlusOutlined />} onClick={() => navigate("report")}>
            Prijavi štetu
          </Button>
        </Layout>
        {global.user ? (
          <UserIcon />
        ) : (
          <>
            <Link to="/register" style={{ margin: "1em" }}>
              <Button>Registracija</Button>
            </Link>
            <Link to="/login">
              <Button type="primary">Prijava</Button>
            </Link>
          </>
        )}
      </Header>
      <Routes>
        {items.map(item => (
          <Route key={item.key} path={item.key as string} element={<item.component />} />
        ))}
        <Route path="report" element={<NewReport />} />
        <Route path="search/:id" element={<Report enableEditing={global.user?.isAdmin} />} />
        <Route path="profile/*" element={<ProfileRoutes />} />
      </Routes>
    </Layout>
  );
}

export default App;
