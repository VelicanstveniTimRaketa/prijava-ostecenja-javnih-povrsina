import { Breadcrumb, Button, Layout, Menu, MenuProps, theme } from "antd";
import { Content, Header } from "antd/es/layout/layout";
import { LaptopOutlined, NotificationOutlined, UserOutlined } from "@ant-design/icons";
import Sider from "antd/es/layout/Sider";
import { createElement } from "react";
import { Link } from "react-router-dom";

const items1: MenuProps["items"] = [
  {
    key: 1,
    label: "Pretraga",
  },
  {
    key: 2,
    label: "Prijavi štetu",
  },
];

const items2: MenuProps["items"] = [UserOutlined, LaptopOutlined, NotificationOutlined].map(
  (icon, index) => {
    const key = String(index + 1);

    return {
      key: `sub${key}`,
      icon: createElement(icon),
      label: `subnav ${key}`,

      children: new Array(4).fill(null).map((_, j) => {
        const subKey = index * 4 + j + 1;
        return {
          key: subKey,
          label: `option${subKey}`,
        };
      }),
    };
  },
);

function App() {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  //const { global, setGlobal } = useContext(StateContext);

  return (
    <Layout>
      <Header style={{ borderBottom: 0, borderTop: "2px", backgroundColor: "white", display: "flex", alignItems: "center", justifyContent: "end" }}>
        <div className="demo-logo" />
        <Menu style={{ display: "flex", padding: "0 1em" }} mode="horizontal" defaultSelectedKeys={["2"]} items={items1} />
        <Link to="/login">
            <Button type="primary">Prijava</Button>
        </Link>
      </Header>
      <Layout>
        <Sider width={200} style={{ background: colorBgContainer }}>
          <Menu
            mode="inline"
            defaultSelectedKeys={["1"]}
            defaultOpenKeys={["sub1"]}
            style={{ height: "100%", borderRight: 0 }}
            items={items2}
          />
        </Sider>
        <Layout style={{ padding: "0 24px 24px" }}>
          <Breadcrumb style={{ margin: "16px 0" }}>
            <Breadcrumb.Item>Home</Breadcrumb.Item>
            <Breadcrumb.Item>List</Breadcrumb.Item>
            <Breadcrumb.Item>App</Breadcrumb.Item>
          </Breadcrumb>
          <Content
            style={{
              padding: 24,
              margin: 0,
              minHeight: 280,
              background: colorBgContainer,
              color: "black",
            }}
          >
            Content
          </Content>
        </Layout>
      </Layout>
    </Layout>
  );
}

export default App;
 