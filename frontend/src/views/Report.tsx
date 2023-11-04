import { Button, Breadcrumb, Layout, Menu, MenuProps, Form, theme, Select, Radio, RadioChangeEvent, notification } from "antd";
import { LaptopOutlined, NotificationOutlined, UserOutlined, PlusOutlined } from "@ant-design/icons";
import { Content } from "antd/es/layout/layout";
import { createElement, useState } from "react";
import Sider from "antd/es/layout/Sider";
import Title from "antd/es/typography/Title";
import TextArea from "antd/es/input/TextArea";
import Upload from "antd/es/upload/Upload";
import MapJsApi from "../components/MapJsApi";
import { useOstecenja } from "../hooks/useOstecenja";
import { useForm } from "antd/es/form/Form";
import { BarebonesPrijava } from "../utils/types";
import { RcFile } from "antd/es/upload";
import { addPrijava } from "../utils/fetch";

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
  const { token: { colorBgContainer } } = theme.useToken();
  const [form] = useForm();
  const [mapInputType, setMapInputType] = useState("onMap");
  const [location, setLocation] = useState<google.maps.LatLng | undefined>(undefined);
  const [images, setImages] = useState<{ originFileObj: RcFile }[]>([]);
  const ostecenja = useOstecenja();

  function getLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(pos => {
        setLocation(new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude));
      }, error => {
        if (error.code == error.PERMISSION_DENIED) {
          notification.warning({
            message: "Vaša lokacija nije dohvaćena",
            description: "Onemogućili ste dohvaćanje Vaše lokacije. Molimo unesite " +
              "lokaciju na neki drugi način ili omogućite dohvaćanje lokacije.",
            placement: "top",
          });
        } else {
          notification.error({
            message: "Vaša lokacija nije dohvaćena",
            description: "Dogodila se pogreška pri dohvaćanju lokacije. Pokušajte ponovno kasnije.",
            placement: "top",
          });
        }
      });
    } else {
      notification.error({
        message: "Vaša lokacija nije dohvaćena",
        description: "Vaš preglednik ne podržava dohvaćanje lokacije. Molimo ažurirajte preglednik.",
        placement: "top",
      });
    }
  }

  function handleMapChange(e: RadioChangeEvent): void {
    e.target.value == "myLocation" ? getLocation() : setMapInputType(e.target.value);
  }

  function locationValidator() {
    return location !== undefined ? Promise.resolve() : Promise.reject();
  }

  async function onSubmit() {
    if (!location) {
      console.error("no location");
      return;
    }
    const prijava: BarebonesPrijava = {
      tipOstecenja: Number.parseInt(form.getFieldValue("tip")),
      opis: form.getFieldValue("opis"),
      latitude: location?.lat(),
      longitude: location?.lng(),
    };

    addPrijava(prijava, images.map(image => image.originFileObj)).then(console.log);
  }

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
            padding: "0 3em",
            margin: 0,
            minHeight: 280,
            background: colorBgContainer,
            color: "black",
          }}
        >
          <Title level={2}>Nova prijava</Title>
          <Form id="addPrijava" form={form} onFinish={onSubmit} labelCol={{ span: "6" }} wrapperCol={{ span: "20" }} style={{ maxWidth: "40em" }}>
            <Form.Item required name="tip" label="Tip" rules={[{ required: true, message: "Molimo označite tip oštećenja." }]}>
              <Select>
                {ostecenja && ostecenja.map(ostecenje => (
                  <Select.Option key={ostecenje.id}>{ostecenje.naziv}</Select.Option>
                ))}
              </Select>
            </Form.Item>
            <Form.Item name="opis" label="Opis">
              <TextArea rows={4} />
            </Form.Item>
            <Form.Item required name="lokacija" label="Lokacija" rules={[{ required: true, validator: locationValidator, message: "Molimo odaberite lokaciju." }]}>
              <div>
                <Radio.Group defaultValue="onMap" onChange={handleMapChange}>
                  <Radio.Button value="onMap">Odaberi na karti</Radio.Button>
                  <Radio.Button value="address">Unesi adresu</Radio.Button>
                  <Radio.Button value="myLocation">Uzmi moju lokaciju</Radio.Button>
                </Radio.Group>
                <Layout style={{ margin: "1em 0" }}>
                  <MapJsApi marker={location} onClick={l => setLocation(l)} disabled={mapInputType !== "onMap"} />
                </Layout>
              </div>
            </Form.Item>
            <Form.Item label="Slike" name="slike" valuePropName="fileList" getValueFromEvent={v => setImages(v.fileList)}>
              <Upload beforeUpload={() => false} accept="image/jpeg, image/png" listType="picture-card">
                <div>
                  <PlusOutlined />
                  <div style={{ marginTop: 8 }}>Učitaj sliku</div>
                </div>
              </Upload>
            </Form.Item>
            <Form.Item key="submit" wrapperCol={{ offset: 6 }}>
              <Button type="primary" htmlType="submit">Prijavi</Button>
            </Form.Item>
          </Form>
        </Content>
      </Layout>
    </Layout>
  );
}

export default Report;
