import { Button, DatePicker, Divider, Form, Layout, Select } from "antd";
import { useForm } from "antd/es/form/Form";
import { Content } from "antd/es/layout/layout";
import { useState } from "react";
import { Prijava } from "../utils/types";
import { PrijaveOptions, getPrijave } from "../utils/fetch";
import { useOstecenja } from "../hooks/useOstecenja";
import ReportList from "../components/ReportList";

function Explore() {
  const [form] = useForm();
  const [data, setData] = useState<Prijava[]>();
  const ostecenja = useOstecenja();

  function onSubmit() {
    const options: PrijaveOptions = {};

    if (form.getFieldValue("active") !== "both") {
      options.active = form.getFieldValue("active");
    }
    if (form.getFieldValue("ostecenje")) {
      options.ostecenjeId = form.getFieldValue("ostecenje");
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
        margin: "2em",
      }}>
        <Form form={form} initialValues={{ active: "true", isChild: "both" }} onFinish={onSubmit} layout="inline" style={{ justifyContent: "center", gap: "1.5em", margin: "1em 0" }}>
          <Form.Item name="ostecenje" label="Tip">
            <Select allowClear style={{ width: "21em" }}>
              {ostecenja && ostecenja.map(ostecenje => (
                <Select.Option key={ostecenje.id}>{ostecenje.naziv}</Select.Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item label="Raspon datuma:" style={{ width: "28em" }}>
            <DatePicker.RangePicker disabled />
          </Form.Item>
          <Form.Item name="active" label="Stanje prijave: ">
            <Select style={{ width: "8em" }}>
              <Select.Option key="true">Otvorena</Select.Option>
              <Select.Option key="false">Zatvorena</Select.Option>
              <Select.Option key="both">Oboje</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="isChild" label="Child prijave: ">
            <Select disabled style={{ width: "9em" }}>
              <Select.Option key="both">Oboje</Select.Option>
              <Select.Option key="true">Samo child</Select.Option>
              <Select.Option key="false">Samo parent</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">Pošalji</Button>
          </Form.Item>
        </Form>
        <Divider />
        {data && <ReportList data={data} />}
      </Content>
    </Layout>
  );
}

export default Explore;
