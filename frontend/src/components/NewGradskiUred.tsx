import { Button, Form, Input, Select, Typography } from "antd";
import { useForm } from "antd/es/form/Form";
import { useOstecenja } from "../hooks/useOstecenja";
import { addGradskiUred } from "../utils/fetch";

function NewGradskiUred() {
  const [form] = useForm();
  const ostecenja = useOstecenja();

  function onSubmit() {
    const data = {
      nazivUreda: form.getFieldValue("uredName"),
      tipOstecenjeID: form.getFieldValue("ostecenje"),
      //osnivac: form.getFieldValue("email"),
      opis: form.getFieldValue("description"),
      noviTipOstecenjeID: form.getFieldValue("newOstecenje"), // string
    };
    addGradskiUred(data).then(
      v => console.log(v)
    );
  }

  return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", flex: 1 }}>
      <Typography.Title level={3}>Novi gradski ured</Typography.Title>
      <Form id="noviUred" form={form} onFinish={onSubmit} labelCol={{ span: 6 }} wrapperCol={{ span: 15 }} style={{ width: "100%", flex: 1 }}>
        <Form.Item label="Naziv: " name="uredName" rules={[{ required: true, message: "Molimo unesite naziv novog ureda" }]}>
          <Input prefix="Ured za " placeholder="..." />
        </Form.Item>
        <Form.Item label="Opis: " name="description" rules={[{ required: true, message: "Molimo unesite opis ureda" }]}>
          <Input.TextArea rows={4} />
        </Form.Item>
        <Form.Item required name="ostecenje" label="Tip oštećenja" rules={[{ required: true, message: "Molimo označite tip oštećenja." }]}>
          <Select onChange={() => form.setFieldValue("ured", undefined)}>
            {ostecenja && ostecenja.map(ostecenje => (
              <Select.Option key={ostecenje.id}>{ostecenje.naziv}</Select.Option>
            ))}
            <Select.Option key="other">Drugi</Select.Option>
          </Select>
        </Form.Item>
        <Form.Item noStyle shouldUpdate={(prevValues, currentValues) => prevValues.ostecenje !== currentValues.ostecenje}>
          {({ getFieldValue }) => getFieldValue("ostecenje") === "other" ? (
            <Form.Item name="newOstecenje" label="Novi tip oštećenja: " rules={[{ required: true, message: "Molimo unesite ime nove vrste oštećenja" }]}>
              <Input prefix="Oštećenje " placeholder="..." />
            </Form.Item>
          ) : null}
        </Form.Item>
        <Form.Item key="submit" wrapperCol={{ offset: 6 }}>
          <Button type="primary" htmlType="submit">Prijavi</Button>
        </Form.Item>
      </Form>
    </div>
  );
}

export default NewGradskiUred;
