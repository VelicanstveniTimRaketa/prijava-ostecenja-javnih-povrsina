import { useContext, useMemo } from "react";
import { StateContext } from "../utils/state";
import { Button, Layout, Menu } from "antd";
import { Header } from "antd/es/layout/layout";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router";
import { PlusOutlined } from "@ant-design/icons";
import { MenuPropsWithComponent } from "../utils/types";
import { Route, Routes } from "react-router";
import WelcomeView from "./WelcomeView";
import Explore from "./Explore";
import NewReport from "./NewReport";
import Report from "./Report";
import Logo from "../components/Logo";
import UserIcon from "../components/UserIcon";
import GradskiUredi from "./GradskiUredi";
import Users from "./Users";
import Check from "../components/Check";
import EditProfile from "./EditProfile";
import Profile from "./Profile";
import UserReports from "./UserReports";

const items: MenuPropsWithComponent = [
  {
    item: {
      key: "",
      label: "Početna",
    },
    component: WelcomeView,
  },
  {
    item: {
      key: "search",
      label: "Pretraga",
    },
    component: Explore,
  },
  {
    item: {
      key: "offices",
      label: "Gradski uredi",
    },
    component: GradskiUredi,
  },
  {
    item: {
      key: "users",
      label: "Korisnici",
    },
    component: Users,
    admin: true,
  },
];

function Main() {
  const { global } = useContext(StateContext);
  const navigate = useNavigate();
  const currentPage = window.location.pathname.split("/")[1];

  const menuItems = useMemo(() => {
    return items
      .filter(i => !i.admin || global.user?.role === "ADMIN" && i.admin)
      .map(i => i.item);
  }, [global.user?.role]);

  return (
    <Layout>
      <Header
        style={{
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
            items={menuItems}
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
          <Route key={item.item.key} path={item.item.key as string} element={<item.component />} />
        ))}
        <Route path="search/:id" element={<Report enableEditing={global.user?.role === "ADMIN"} />} />
        <Route path="users" element={<Users />} />
        <Route path="report" element={<NewReport />} />
        <Route path="profile/*" element={
          <Check if={!!global.user} elseNavigateTo="/login">
            <Routes>
              <Route path="" element={<Profile />} />
              <Route path="reports" element={<UserReports />} />
              <Route path="reports/:id" element={<Report enableEditing={true} />} />
              <Route path="edit" element={<EditProfile />} />
            </Routes>
          </Check>
        } />
      </Routes>
    </Layout>
  );
}

export default Main;
