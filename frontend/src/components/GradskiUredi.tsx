import { useContext } from "react";
import { Button, Divider, Row, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { useGradskiUredi } from "../hooks/useGradskiUredi";
import { StateContext } from "../utils/state";
import CustomList from "./CustomList";

function GradskiUredi() {
  const { global } = useContext(StateContext);
  const uredi = useGradskiUredi();

  const items = uredi?.map(ured => {
    const button = <Button style={{ marginLeft: "2em" }} onClick={() => { }} type="primary">Prijavi se</Button>;
    return {
      id: ured.id,
      onClick: () => { },
      items: [
        { title: "ID:", value: ured.id },
        { title: "Naziv:", value: ured.naziv },
        { title: "Oštećenje:", value: ured.ostecenje?.naziv },
        ...(global.user ? [{ value: button }] : [])
      ]
    };
  });


  return (
    <Content style={{ display: "flex", margin: "2em", textAlign: "center" }}>
      <Row style={{ justifyContent: "space-around", flex: 1 }}>
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", flex: 2 }}>
          <Typography.Title level={3}>Lista ureda</Typography.Title>
          {items && <CustomList data={items} />}
        </div>
        {global.user &&
          <>
            <Divider type="vertical" style={{ minHeight: "100%" }} />
            <div style={{ display: "flex", flex: 1, marginTop: "10em", justifyContent: "center" }}>
              <Button type="primary">Novi gradski ured</Button>
            </div>
          </>
        }
      </Row>
    </Content>
  );
}

export default GradskiUredi;