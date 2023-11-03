import { Button, DatePicker, Divider, Form, Layout, List, Select } from "antd";
import { useForm } from "antd/es/form/Form";
import { Content } from "antd/es/layout/layout";
import { useState } from "react";
import { Prijava } from "../utils/types";
import { getPrijave } from "../utils/fetch";
import PrijavaListItemField from "../components/PrijavaListItemField";
import { useOstecenja } from "../hooks/useOstecenja";

function Explore() {
  const [form] = useForm();
  const [data, setData] = useState<Prijava[]>();
  const ostecenja = useOstecenja();

  function onSubmit() {
    getPrijave()
      .then(res => setData(res.data))
      .catch(error => console.info(error));
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
        <Form form={form} onFinish={onSubmit} layout="inline" style={{ justifyContent: "center", gap: "1.5em", margin: "1em 0" }}>
          <Form.Item label="Tip">
            <Select allowClear style={{ width: "21em" }}>
              {ostecenja && ostecenja.map(ostecenje => (
                <Select.Option key={ostecenje.id}>{ostecenje.naziv}</Select.Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item label="Raspon datuma:" style={{ width: "28em" }}>
            <DatePicker.RangePicker />
          </Form.Item>
          <Form.Item label="Stanje prijave: ">
            <Select defaultValue="open" style={{ width: "8em" }}>
              <Select.Option key="open">Otvorena</Select.Option>
              <Select.Option key="closed">Zatvorena</Select.Option>
              <Select.Option key="both">Oboje</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item label="Child prijave: ">
            <Select defaultValue="both" style={{ width: "9em" }}>
              <Select.Option key="both">Oboje</Select.Option>
              <Select.Option key="child">Samo child</Select.Option>
              <Select.Option key="parent">Samo parent</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">Pošalji</Button>
          </Form.Item>
        </Form>
        <Divider />
        {data && (
          <List bordered style={{ margin: "1em 0", width: "fit-content" }}>
            {data.map(prijava => (
              <List.Item key={prijava.id} style={{ padding: "1.5em 2em", display: "flex", textAlign: "center" }}>
                <PrijavaListItemField title="ID:" text={prijava.id} />
                <PrijavaListItemField title="Tip oštećenja:" text={prijava.tipOstecenja.naziv} />
                <PrijavaListItemField title="Prijavitelj:" text={prijava.kreator?.username || "Anoniman"} />
              </List.Item>
            ))}
          </List>
        )}
      </Content>
    </Layout>
  );
}

export default Explore;
