import { Form, Input, Select, Radio, Layout, Upload, Modal, Typography, Button } from "antd";
import TextArea from "antd/es/input/TextArea";
import MapJsApi from "./MapJsApi";
import PlacesInput from "./PlacesInput";
import { useForm } from "antd/es/form/Form";
import { RcFile } from "antd/es/upload";
import EXIF from "exif-js";
import { checkLocationComponents, componentsToGoogle, getLocation, locationToGoogle } from "../utils/location";
import { BarebonesPrijava, Prijava } from "../utils/types";
import { useEffect, useState } from "react";
import { useGradskiUredi } from "../hooks/useGradskiUredi";
import { useOstecenja } from "../hooks/useOstecenja";
import { PlusOutlined } from "@ant-design/icons";

interface NewReportFormProps {
  initialData?: Prijava;
  onSubmit: (prijava: BarebonesPrijava) => void;
  setImages?: (images: { originFileObj: RcFile }[]) => void;
  loading?: boolean;
}

function NewReportForm(props: NewReportFormProps) {
  const [form] = useForm();
  const [mapInputType, setMapInputType] = useState("onMap");
  const [location, setLocation] = useState<google.maps.LatLngLiteral | undefined>(props.initialData && (locationToGoogle(props.initialData.lokacija)));
  const [center, setCenter] = useState<google.maps.LatLngLiteral | undefined>(undefined);
  const [locationFromImage, setLocationFromImage] = useState<google.maps.LatLngLiteral | undefined>(undefined);
  const ostecenja = useOstecenja();
  const uredi = useGradskiUredi();

  function locationValidator() {
    return location !== undefined ? Promise.resolve() : Promise.reject();
  }

  useEffect(() => {
    if (mapInputType === "myLocation") getLocation().then(l => { setLocation(l); setCenter(l); });
  }, [mapInputType]);

  function onImageUpload(file: RcFile) {
    file.arrayBuffer().then(v => {
      const data = EXIF.readFromBinaryFile(v);
      if (data && data.GPSLatitude && data.GPSLongitude && checkLocationComponents(data.GPSLatitude) && checkLocationComponents(data.GPSLongitude)) {
        setLocationFromImage(componentsToGoogle(data.GPSLatitude, data.GPSLongitude));
      }
    });
    return false;
  }

  function onSubmit() {
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
    props.onSubmit(prijava);
  }
  const initialData = props.initialData && {
    reportName: props.initialData.naziv,
    opis: props.initialData.opis,
    ostecenja: props.initialData.gradskiUred.tipOstecenja.id,
    ured: props.initialData.gradskiUred.id,
  };

  return (
    <Form id="addPrijava" form={form} onFinish={onSubmit} initialValues={initialData} labelCol={{ span: "5" }} wrapperCol={{ span: "20" }} style={{ width: "100%" }}>
      <Form.Item required name="reportName" label="Naziv: " rules={[{ required: true, message: "Molimo unesite naziv prijave." }]}>
        <Input />
      </Form.Item>
      <Form.Item name="opis" label="Opis: ">
        <TextArea rows={4} />
      </Form.Item>
      <Form.Item required name="ostecenja" label="Tip oštećenja" rules={[{ required: true, message: "Molimo označite tip oštećenja." }]}>
        <Select onChange={() => form.setFieldValue("ured", undefined)}>
          {ostecenja && ostecenja.map(ostecenje => (
            <Select.Option key={ostecenje.id} value={ostecenje.id}>{ostecenje.naziv}</Select.Option>
          ))}
        </Select>
      </Form.Item>
      <Form.Item required label="Gradski ured" shouldUpdate={(prevValues, currentValues) => prevValues.ostecenja !== currentValues.ostecenja}>
        {({ getFieldValue }) => {
          const filtriraniUredi = uredi?.filter(ured => ured.tipOstecenja.id === getFieldValue("ostecenja"));
          return (
            <Form.Item noStyle name="ured" rules={[{ required: true, message: "Molimo označite gradski ured." }]}>
              <Select disabled={!getFieldValue("ostecenja")}>
                {filtriraniUredi && filtriraniUredi.map(ured => (
                  <Select.Option key={ured.id} value={ured.id}>{ured.naziv}</Select.Option>
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
      {props.setImages !== undefined ? (
        <Form.Item label="Slike" name="slike" valuePropName="fileList" getValueFromEvent={v => props.setImages && props.setImages(v.fileList)}>
          <Upload beforeUpload={onImageUpload} accept="image/jpeg, image/png" listType="picture-card">
            <div>
              <PlusOutlined />
              <div style={{ marginTop: 8 }}>Učitaj sliku</div>
            </div>
          </Upload>
        </Form.Item>
      ) : null}
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
        <Button type="primary" loading={props.loading} htmlType="submit">Prijavi</Button>
      </Form.Item>
    </Form>
  );
}

export default NewReportForm;