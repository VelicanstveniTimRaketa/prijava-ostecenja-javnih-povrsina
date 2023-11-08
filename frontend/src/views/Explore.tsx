import { Button, DatePicker, Divider, Form, Layout, Select, Typography } from "antd";
import { CloseCircleFilled } from "@ant-design/icons";
import { useForm } from "antd/es/form/Form";
import { Content } from "antd/es/layout/layout";
import { useState } from "react";
import { Prijava } from "../utils/types";
import { PrijaveOptions, getPrijave } from "../utils/fetch";
import { useOstecenja } from "../hooks/useOstecenja";
import { Dayjs } from "dayjs";
import { useToggleable } from "../hooks/useToggleable";
import locale from "antd/es/date-picker/locale/hr_HR";
import ReportList from "../components/ReportList";
import MapJsApi from "../components/MapJsApi";

const styleLocationActive = {
  color: "green",
  borderColor: "green",
  paddingRight: "0.5em",
  justifyContent: "space-between",

};

function Explore() {
  const [form] = useForm();
  const [data, setData] = useState<Prijava[]>();
  const [location, setLocation] = useState<google.maps.LatLng | undefined>(undefined);
  const [locationActive, toggleLocation, ref] = useToggleable(false);
  const ostecenja = useOstecenja();

  function onSubmit() {
    const options: PrijaveOptions = {};

    if (form.getFieldValue("active") !== "both") {
      options.active = form.getFieldValue("active");
    }
    if (form.getFieldValue("ostecenje")) {
      options.ostecenjeId = form.getFieldValue("ostecenje");
    }
    if (form.getFieldValue("dates")) {
      const dates: Dayjs[] = form.getFieldValue("dates");
      options.dateFrom = dates[0].toISOString();
      options.dateTo = dates[1].toISOString();
    }
    if (location) {
      options.lat = location.lat().toString();
      options.lng = location.lat().toString();
    }

    getPrijave(options).then(res => setData(res.data));
  }

  return (
    <Layout style={{ display: "flex", alignItems: "center" }}>
      <Content style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: "fit-content",
        height: "100%",
        color: "black",
        margin: "0 2em 2em 2em",
      }}>
        <Typography.Title level={2}>Pretraži sve prijave</Typography.Title>
        <Form
          form={form}
          initialValues={{ active: "true", isChild: "both" }}
          onFinish={onSubmit}
          layout="inline"
          style={{ justifyContent: "center", gap: "1.5em", margin: "1em 0" }}
        >
          <Form.Item name="ostecenje" label="Tip">
            <Select allowClear style={{ width: "21em" }}>
              {ostecenja && ostecenja.map(ostecenje => (
                <Select.Option key={ostecenje.id}>{ostecenje.naziv}</Select.Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item name="dates" label="Raspon datuma:" style={{ width: "28em" }}>
            <DatePicker.RangePicker locale={locale} />
          </Form.Item>
          <Form.Item name="lokacija" label="Približna lokacija: ">
            <div style={{ position: "relative", width: "8em" }}>
              <Button
                type="dashed"
                className="parentIconHover"
                onClick={toggleLocation}
                style={{
                  display: "flex",
                  justifyContent: "center",
                  width: "100%",
                  ...(location ? styleLocationActive : {})
                }}
              >
                <span style={{ margin: 0 }}>
                  {location ? "Odabrano" : "Odaberi"}
                </span>
                {location && (
                  <CloseCircleFilled
                    className="iconHover"
                    style={{ display: "flex", borderRadius: "100em" }}
                    onClick={(e) => { e.stopPropagation(); setLocation(undefined); }}
                  />
                )}
              </Button>
              {locationActive && (
                <div
                  ref={ref}
                  style={{
                    position: "absolute",
                    zIndex: 1,
                    right: 0,
                    top: "100%",
                    width: "15em",
                    marginTop: "1em",
                  }}>
                  <MapJsApi marker={location} onClick={setLocation} />
                </div>
              )}
            </div>
          </Form.Item>
          <Form.Item name="active" label="Stanje prijave: ">
            <Select style={{ width: "8em" }}>
              <Select.Option key="true">Otvorena</Select.Option>
              <Select.Option key="false">Zatvorena</Select.Option>
              <Select.Option key="both">Oboje</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">Pošalji</Button>
          </Form.Item>
        </Form>
        <Divider />
        {data && <>
          <Typography.Title level={5} style={{ margin: "0.5em" }}>Pronađeni broj prijava: {data.length}</Typography.Title>
          <ReportList data={data} />
        </>}
      </Content>
    </Layout>
  );
}

export default Explore;
