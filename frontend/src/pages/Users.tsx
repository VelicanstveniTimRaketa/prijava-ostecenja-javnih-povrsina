import { Content } from "antd/es/layout/layout";
import { Button, Checkbox, Typography, notification } from "antd";
import { deleteUser, getAllUsers } from "../utils/fetch";
import { useState, useEffect } from "react";
import { User, Response } from "../utils/types";
import CustomList from "../components/CustomList";

function Users() {
  const [users, setUsers] = useState<User[]>();
  useEffect(() => {
    getAllUsers().then(res => setUsers(res.data));
  }, []);

  function userRemovedHandler(e: Response<unknown>) {
    if (e.success) {
      notification.success({ message: "Korisnik uspješno izbrisan", description: "", placement: "top" });
      getAllUsers().then(res => setUsers(res.data));
    } else {
      notification.error({ message: "Pogreška pri brisanju korisnika", description: e.errors && e.errors[0], placement: "top" });
    }
  }

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
        value: user.ured?.naziv || "Nema",
      },
      {
        title: "Administrator",
        value: <Checkbox disabled defaultChecked={user.role === "ADMIN"} />
      },
      {
        value: <Button onClick={() => deleteUser(user.id).then(userRemovedHandler)}>Izbriši</Button>
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
