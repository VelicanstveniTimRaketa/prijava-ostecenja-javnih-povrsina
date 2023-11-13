import { useContext, useState } from "react";
import { Button, Divider, Row, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { useGradskiUredi } from "../hooks/useGradskiUredi";
import { StateContext } from "../utils/state";
import CustomList from "../components/CustomList";
import NewGradskiUred from "../components/NewGradskiUred";

function GradskiUredi() {
  const { global } = useContext(StateContext);
  const uredi = useGradskiUredi();
  const [newUred, setNewUred] = useState(false);

  const items = uredi?.map(ured => {
    const button = <Button style={{ marginLeft: "2em" }} onClick={() => { }} type="primary">Prijavi se</Button>;
    return {
      id: ured.id,
      onClick: () => { },
      items: [
        { title: "ID:", value: ured.id },
        { title: "Naziv:", value: ured.naziv },
        { title: "Oštećenje:", value: ured.tipOstecenja?.naziv },
        ...(global.user ? [{ value: button }] : [])
      ]
    };
  });


  return (
    <Content style={{ display: "flex", margin: "2em" }}>
      <Row style={{ justifyContent: "space-around", flex: 1 }}>
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", flex: 2 }}>
          <Typography.Title level={3}>Lista ureda</Typography.Title>
          {items && <CustomList data={items} />}
        </div>
        {global.user &&
          <>
            <Divider type="vertical" style={{ minHeight: "100%" }} />
            <div style={{ display: "flex", flex: 1, marginTop: "8em", justifyContent: "center" }}>
              {!newUred && <Button onClick={() => setNewUred(true)} type="primary">Novi gradski ured</Button>}
              {newUred && <NewGradskiUred />}
            </div>
          </>
        }
      </Row>
    </Content>
  );
}

export default GradskiUredi;