import { Button, DatePicker, Form, Layout, List, Select } from "antd";
import { useForm } from "antd/es/form/Form";
import { Content } from "antd/es/layout/layout";
import { useState } from "react";
import { Prijava } from "../utils/types";
import { getPrijave } from "../utils/fetch";
import PrijavaListItemField from "../components/PrijavaListItemField";

function Explore() {
  const [form] = useForm();
  const [data, setData] = useState<Prijava[]>();

  function onSubmit() {
    getPrijave()
      .then(res => setData(res.data))
      .catch(error => console.info(error));
  }

  console.log(data);

  return (
    <Layout style={{ display: "flex", alignItems: "center" }}>
      <Content style={{ display: "flex", flexDirection: "column", alignItems: "center", width: "fit-content", height: "100%", color: "black", margin: "2em" }}>
        <Form form={form} onFinish={onSubmit} layout="inline">
          <Form.Item label="Tip">
            <Select allowClear style={{ width: "15em" }}>
              <Select.Option key="rupa-u-kolniku">Rupa u kolniku</Select.Option>
              <Select.Option key="puknuta-tramvajska-zica">Puknuće tramvajske žice</Select.Option>
              <Select.Option key="ostecen-prometni-znak">Oštećen prometni znak</Select.Option>
              <Select.Option key="ostalo">Ostalo</Select.Option>
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
        {data && (
          <List bordered style={{ margin: "2em 0", width: "fit-content" }}>
            {data.map(prijava => (
              <List.Item key={prijava.id} style={{ padding: "1.5em 2em", display: "flex", textAlign: "center" }}>
                <PrijavaListItemField title="ID:" text={prijava.id} />
                <PrijavaListItemField title="Tip oštećenja:" text={prijava.tipOstecenja.naziv} />
                <PrijavaListItemField title="Kreator:" text={prijava.kreatorId?.username} />
              </List.Item>
            ))}
          </List>
        )}
      </Content>
    </Layout>
  );
}

export default Explore;
