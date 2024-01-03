import { Content } from "antd/es/layout/layout";
import { useUsers } from "../hooks/useUsers";
import { Button, Checkbox, Typography } from "antd";
import CustomList from "../components/CustomList";

function Users() {
  const users = useUsers();
  const data = users?.map(user => ({
    id: user.id,
    items: [
      {
        title: "ID:",
        value: user.id,
      },
      {
        title: "Korisničko ime:",
        value: user.username,
      },
      {
        title: "Email",
        value: user.email,
      },
      {
        title: "Ured",
        value: user.ured?.naziv || "Nema"
      },
      {
        title: "Administrator",
        value: <Checkbox disabled defaultChecked={user.role === "ADMIN"} />
      },
      {
        value: <Button>Izbriši</Button>
      },
    ]
  }));
  return (
    <Content style={{ margin: "2em", display: "flex", flexDirection: "column", alignItems: "center" }}>
      <Typography.Title level={2}>Korisnici</Typography.Title>
      {data && <CustomList data={data} />}
    </Content>
  );
}

export default Users;
