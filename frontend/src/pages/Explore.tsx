import { Button, DatePicker, Divider, Form, Select, Typography } from "antd";
import { CloseCircleFilled } from "@ant-design/icons";
import { useForm } from "antd/es/form/Form";
import { Content } from "antd/es/layout/layout";
import { useEffect, useState } from "react";
import { Prijava, PrijaveOptions } from "../utils/types";
import { getPrijave } from "../utils/fetch";
import { useOstecenja } from "../hooks/useOstecenja";
import { Dayjs } from "dayjs";
import { useToggleable } from "../hooks/useToggleable";
import { locationToGoogle } from "../utils/location";
import locale from "antd/es/date-picker/locale/hr_HR";
import ReportList from "../components/ReportList";
import MapJsApi from "../components/MapJsApi";
import AlertBanner from "../components/AlertBanner";

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
  const [selectedPrijava, setSelectedPrijava] = useState<Prijava>();
  const [locationActive, toggleLocation, ref] = useToggleable(false);
  const [loading, setLoading] = useState(false);
  const ostecenja = useOstecenja();

  useEffect(() => {
    onSubmit();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

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
      options.lng = location.lng().toString();
    }

    setLoading(true);
    getPrijave(options).then(res => {
      setData(res.data?.sort((p1, p2) => p2.prvoVrijemePrijave.getTime() - p1.prvoVrijemePrijave.getTime()));
      setLoading(false);
    });
  }

  const selectedPrijavaSpot = selectedPrijava && locationToGoogle(selectedPrijava.lokacija);

  return (
    <Content style={{ display: "flex", flexDirection: "column", alignItems: "center", margin: "2em" }}>
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
        <Form.Item name="dates" label="Prijavljeni između:" style={{ width: "40em" }}>
          <DatePicker.RangePicker showTime locale={locale} />
        </Form.Item>
        <Form.Item label="Približna lokacija: " style={{ position: "static" }}>
          <div style={{ width: "8em" }}>
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
            <div
              ref={ref}
              style={{
                position: "absolute",
                zIndex: locationActive ? 10 : -1,
                opacity: locationActive ? 1 : 0,
                transition: "200ms",
                top: "100%",
                right: 0,
                marginTop: "1em",
              }}>
              <MapJsApi marker={location} onClick={setLocation} />
            </div>
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
          <Button type="primary" loading={loading} htmlType="submit">Pošalji</Button>
        </Form.Item>
      </Form>
      <Divider />
      {data && <>
        <Typography.Title level={5} style={{ margin: "0.5em" }}>Broj pronađenih prijava: {data.length}</Typography.Title>
        <div style={{ display: "flex" }}>
          <ReportList onClick={p => setSelectedPrijava({ ...p })} data={data} />
          {data.length > 0 ?
            <MapJsApi
              style={{ display: "flex", position: "sticky", top: "30vh", margin: "1em 2em" }}
              marker={selectedPrijavaSpot}
              center={selectedPrijavaSpot}
              zoom={selectedPrijava && 15}
              secondaryMarkers={selectedPrijava ? undefined : data.map(p => locationToGoogle(p.lokacija))}
            />
            :
            <AlertBanner message="Nema prijava za odabrane filtere!" />
          }
        </div>
      </>}
    </Content>
  );
}

export default Explore;
