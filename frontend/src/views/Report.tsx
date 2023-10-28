import { Breadcrumb, Layout, Menu, MenuProps, Form, theme, Select } from "antd";
import { LaptopOutlined, NotificationOutlined, UserOutlined, PlusOutlined } from "@ant-design/icons";
import { Content } from "antd/es/layout/layout";
import { createElement } from "react";
import Sider from "antd/es/layout/Sider";
import Title from "antd/es/typography/Title";
import TextArea from "antd/es/input/TextArea";
import Upload from "antd/es/upload/Upload";
import { Button } from "antd";
import MapEmbed from "../components/MapEmbed";

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


function Report() {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
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
      <Layout style={{ padding: "0 2em 2em" }}>
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
          <Title level={2}>Nova prijava</Title>
          <Form labelCol={{ span: "6" }} wrapperCol={{ span: "20" }} style={{ maxWidth: "40em" }}>
            <Form.Item required label="Tip">
              <Select>
                <Select.Option key="rupa-u-kolniku">Rupa u kolniku</Select.Option>
                <Select.Option key="puknuta-tramvajska-zica">Puknuće tramvajske žice</Select.Option>
                <Select.Option key="ostecen-prometni-znak">Oštećen prometni znak</Select.Option>
                <Select.Option key="ostalo">Ostalo</Select.Option>
              </Select>
            </Form.Item>
            <Form.Item label="Opis">
              <TextArea rows={4}></TextArea>
            </Form.Item>
            <Form.Item required label="Lokacija">
              <MapEmbed />
            </Form.Item>
            <Form.Item label="slike" valuePropName="fileList" getValueFromEvent={(file) => console.log(file)}>
              <Upload action="/upload" accept="image/jpeg, image/png" listType="picture-card">
                <div>
                  <PlusOutlined />
                  <div style={{ marginTop: 8 }}>Učitaj slike</div>
                </div>
              </Upload>
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 6 }}>
              <Button type="primary" htmlType="submit">Prijavi</Button>
            </Form.Item>
          </Form>
        </Content>
      </Layout>
    </Layout>
  );
}

export default Report;