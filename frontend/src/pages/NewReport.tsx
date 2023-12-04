import { Button, Layout, Form, theme, Select, Radio, Input, Modal, Typography, notification } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import { Content } from "antd/es/layout/layout";
import { useEffect, useState } from "react";
import { useOstecenja } from "../hooks/useOstecenja";
import { useGradskiUredi } from "../hooks/useGradskiUredi";
import { useForm } from "antd/es/form/Form";
import { RcFile } from "antd/es/upload";
import { AddPrijavaResponse, BarebonesPrijava, Response } from "../utils/types";
import { addPrijava, connectPrijave } from "../utils/fetch";
import { checkLocationComponents, componentsToGoogle, getLocation } from "../utils/location";
import { useNavigate } from "react-router";
import Title from "antd/es/typography/Title";
import TextArea from "antd/es/input/TextArea";
import Upload from "antd/es/upload/Upload";
import MapJsApi from "../components/MapJsApi";
import PlacesInput from "../components/PlacesInput";
import ReportList from "../components/ReportList";
import EXIF from "exif-js";

function NewReport() {
  const { token: { colorBgContainer } } = theme.useToken();
  const [form] = useForm();
  const [mapInputType, setMapInputType] = useState("onMap");
  const [location, setLocation] = useState<google.maps.LatLngLiteral | undefined>(undefined);
  const [center, setCenter] = useState<google.maps.LatLngLiteral | undefined>(undefined);
  const [locationFromImage, setLocationFromImage] = useState<google.maps.LatLngLiteral | undefined>(undefined);
  const [images, setImages] = useState<{ originFileObj: RcFile }[]>([]);
  const [response, setResponse] = useState<Response<AddPrijavaResponse>>();
  const ostecenja = useOstecenja();
  const uredi = useGradskiUredi();
  const navigate = useNavigate();

  function success(message: string) {
    notification.success({
      message,
      description: "",
      placement: "top",
    });
    navigate("/search");
  }

  function fail(response: Response<unknown>) {
    notification.error({
      message: "Prijava nije registrirana",
      description: response.errors && response.errors[0],
      placement: "top",
    });
  }

  useEffect(() => {
    if (mapInputType === "myLocation") getLocation().then(l => { setLocation(l); setCenter(l); });
  }, [mapInputType]);

  useEffect(() => {
    if (!response) return;

    if (!response.success) {
      fail(response);
      return;
    }
    if (response.data && response.data.nearbyReports.length > 0) return;

    success("Prijava uspješno registrirana");
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [navigate, response]);

  function locationValidator() {
    return location !== undefined ? Promise.resolve() : Promise.reject();
  }

  async function onSubmit() {
    if (!location) {
      console.error("no location");
      return;
    }
    const prijava: BarebonesPrijava = {
      naziv: form.getFieldValue("reportName"),
      opis: form.getFieldValue("opis"),
      ured: Number.parseInt(form.getFieldValue("ured")),
      latitude: location?.lat,
      longitude: location?.lng,
    };

    addPrijava(prijava, images.map(image => image.originFileObj)).then(setResponse);
  }

  function onImageUpload(file: RcFile) {
    file.arrayBuffer().then(v => {
      const data = EXIF.readFromBinaryFile(v);
      if (data && data.GPSLatitude && data.GPSLongitude && checkLocationComponents(data.GPSLatitude) && checkLocationComponents(data.GPSLongitude)) {
        setLocationFromImage(componentsToGoogle(data.GPSLatitude, data.GPSLongitude));
      }
    });
    return false;
  }

  return (
    <Layout style={{ margin: "3em", display: "flex", alignItems: "center" }}>
      <Content
        style={{
          display: "flex",
          flexDirection: "column",
          maxWidth: "100%",
          alignItems: "center",
          padding: "2em 6em",
          background: colorBgContainer,
        }}
      >
        <Title level={2}>Nova prijava</Title>
        <Form id="addPrijava" form={form} onFinish={onSubmit} labelCol={{ span: "5" }} wrapperCol={{ span: "20" }} style={{ width: "100%" }}>
          <Form.Item required name="reportName" label="Naziv: " rules={[{ required: true, message: "Molimo unesite naziv prijave." }]}>
            <Input />
          </Form.Item>
          <Form.Item name="opis" label="Opis: ">
            <TextArea rows={4} />
          </Form.Item>
          <Form.Item required name="ostecenja" label="Tip oštećenja" rules={[{ required: true, message: "Molimo označite tip oštećenja." }]}>
            <Select onChange={() => form.resetFields(["ured"])}>
              {ostecenja && ostecenja.map(ostecenje => (
                <Select.Option key={ostecenje.id}>{ostecenje.naziv}</Select.Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item required label="Gradski ured" shouldUpdate={(prevValues, currentValues) => prevValues.ostecenja !== currentValues.ostecenja}>
            {({ getFieldValue }) => {
              const filtriraniUredi = uredi?.filter(ured => ured.tipOstecenja.id.toString() === getFieldValue("ostecenja"));
              return (
                <Form.Item noStyle name="ured" rules={[{ required: true, message: "Molimo označite gradski ured." }]}>
                  <Select disabled={!getFieldValue("ostecenja")}>
                    {filtriraniUredi && filtriraniUredi.map(ostecenje => (
                      <Select.Option key={ostecenje.id}>{ostecenje.naziv}</Select.Option>
                    ))}
                  </Select>
                </Form.Item>
              );
            }}
          </Form.Item>
          <Form.Item required name="lokacija" label="Lokacija" rules={[{ required: true, validator: locationValidator, message: "Molimo odaberite lokaciju." }]}>
            <div>
              <Radio.Group value={mapInputType} onChange={e => setMapInputType(e.target.value)}>
                <Radio.Button value="onMap">Odaberi na karti</Radio.Button>
                <Radio.Button value="address">Unesi adresu</Radio.Button>
                <Radio.Button value="myLocation">Uzmi moju lokaciju</Radio.Button>
              </Radio.Group>
              {mapInputType === "address" && <PlacesInput onClick={l => { setLocation(l); setCenter(l); setMapInputType("onMap"); }} />}
              <Layout style={{ margin: "1em 0" }}>
                <MapJsApi marker={location} center={center} zoom={center && 12} onClick={l => setLocation({ lat: l.lat(), lng: l.lng() })} disabled={mapInputType !== "onMap"} />
              </Layout>
            </div>
          </Form.Item>
          <Form.Item label="Slike" name="slike" valuePropName="fileList" getValueFromEvent={v => setImages(v.fileList)}>
            <Upload beforeUpload={onImageUpload} accept="image/jpeg, image/png" listType="picture-card">
              <div>
                <PlusOutlined />
                <div style={{ marginTop: 8 }}>Učitaj sliku</div>
              </div>
            </Upload>
          </Form.Item>
          {locationFromImage && (
            <Modal
              open={!!locationFromImage}
              okText="Koristi"
              cancelText="Ne koristi"
              onOk={() => { setLocation(locationFromImage); setCenter(locationFromImage); setLocationFromImage(undefined); }}
              onCancel={() => setLocationFromImage(undefined)}
            >
              <Typography.Title level={2}>Otkrivena je lokacija!</Typography.Title>
              <Typography.Text>Pronađena je lokacija u učitanoj slici. Želite li je koristiti kao lokaciju oštećenja?</Typography.Text>
            </Modal>
          )}
          <Form.Item key="submit" wrapperCol={{ offset: 5 }}>
            <Button type="primary" htmlType="submit">Prijavi</Button>
          </Form.Item>
        </Form>
      </Content>
      {response?.data && response.data.nearbyReports.length !== 0 && (
        <Modal
          style={{ minWidth: "fit-content" }}
          open={!!response}
          cancelText="Preskoči"
          onCancel={() => navigate("/search")}
          footer={(_, { CancelBtn }) => <CancelBtn />}
        >
          <Typography.Title level={2}>Pronađene su prijave u blizini!</Typography.Title>
          <Typography.Title level={5}>Pogledajte odnosi li se ikoja na Vašu i povežite svoju prijavu s njome.</Typography.Title>
          <ReportList buttonText="Poveži" data={response.data.nearbyReports} onButtonClick={(p) => {
            connectPrijave(response.data?.newReport.id as number, p.id).then(res => {
              res.success ? success("Prijava uspješno registrirana i povezana") : fail(res);
            });
          }} />
        </Modal>
      )}
    </Layout>
  );
}

export default NewReport;
