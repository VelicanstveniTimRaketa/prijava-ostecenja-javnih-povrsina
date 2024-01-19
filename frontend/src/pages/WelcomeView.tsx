import { Layout, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getPrijave } from "../utils/fetch";
import { locationToGoogle } from "../utils/location";
import BigButton from "../components/BigButton";
import MapJsApi from "../components/MapJsApi";
import Logo from "../components/Logo";

function WelcomeView() {
  const navigate = useNavigate();
  const [prijavaLocations, setPrijavaLocations] = useState<(google.maps.LatLng | google.maps.LatLngLiteral)[]>();

  useEffect(() => {
    const startDate = new Date();
    startDate.setDate(startDate.getDate() - 7);

    const settings = {
      active: "true",
      //dateFrom: startDate.toISOString(),
      //dateTo: new Date().toISOString(),
    };
    getPrijave(settings).then(v => setPrijavaLocations(v.data?.map(p => locationToGoogle(p.lokacija))));
  }, []);

  return (
    <Layout style={{ padding: "5em 4em", display: "flex", flexDirection: "row" }} className="bgAnim">
      <Content style={{ display: "flex", flexDirection: "column", minHeight: 280, alignItems: "center", height: "fit-content" }}>
        <Logo size="8em" />
        <Typography.Title level={1}>Prijavi oštećenja na javnim površinama</Typography.Title>
        <Typography.Paragraph style={{ fontSize: "1.5em" }}>
          Rješavaju se u rekordnom roku!
        </Typography.Paragraph>
        <div style={{ display: "flex", flexDirection: "row", gap: "2em" }}>
          <BigButton onClick={() => navigate("search")} bold>Pretraži postojeća</BigButton>
          <BigButton onClick={() => navigate("report")} bold>Prijavi novo</BigButton>
        </div>
        <Typography.Title level={4} style={{ marginTop: "2em" }}>Nedavno prijavljena oštećenja u blizini:</Typography.Title>
        <MapJsApi secondaryMarkers={prijavaLocations} />
      </Content>
    </Layout>
  );
}

export default WelcomeView;
