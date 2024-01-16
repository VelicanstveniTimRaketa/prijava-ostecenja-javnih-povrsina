import { Layout, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import Title from "antd/es/typography/Title";
import BigButton from "../components/BigButton";
import MapJsApi from "../components/MapJsApi";
import Logo from "../components/Logo";

function WelcomeView() {
  const navigate = useNavigate();

  const background = "linear-gradient(30deg, rgb(115, 140, 199) 0%, rgb(215, 222, 232) 35%, rgb(248, 248, 249) 100%)";

  return (
    <Layout style={{ padding: "2em 4em", display: "flex", flexDirection: "row", background }}>
      <Content style={{ display: "flex", flexDirection: "column", margin: "2em", minHeight: 280, alignItems: "center", height: "fit-content" }}>
        <Logo size="8em" />
        <Title level={1}>Prijavi oštećenje na cesti</Title>
        <Typography.Paragraph style={{ fontSize: "1.5em" }}>
          Rješavaju se u rekordnom roku!
        </Typography.Paragraph>
        <div style={{ display: "flex", flexDirection: "row", gap: "1em" }}>
          <BigButton onClick={() => navigate("search")} bold>Pretraži</BigButton>
          <BigButton onClick={() => navigate("report")} bold>Prijavi</BigButton>
        </div>
        <MapJsApi style={{ margin: "2em" }} />
      </Content>
    </Layout>
  );
}

export default WelcomeView;
