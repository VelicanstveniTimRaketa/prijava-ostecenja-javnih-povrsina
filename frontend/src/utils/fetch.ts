import { RcFile } from "antd/es/upload";
import { BarebonesPrijava, GradskiUred, LoginData, Prijava, PrijaveOptions, Response, TipOstecenja, User, UserLogin, UserRegiser } from "./types";

export class Fetcher {
  static token?: string;

  static setToken(token?: string) {
    this.token = token;
  }

  static get<T>(path: string, params?: Record<string, string>): Promise<Response<T>> {
    const headers = this.token ? { Authorization: "Bearer " + this.token } : undefined;
    let fullPath = path;
    if (params) fullPath += "?" + new URLSearchParams(params);

    return new Promise(res => {
      fetch(fullPath, { headers })
        .then(resp => resp.json())
        .then(res)
        .catch((error) => res({ success: false, errors: [error.toString()] }));
    });
  }

  static post<T>(path: string, data: FormData | unknown): Promise<Response<T>> {
    const headers: HeadersInit = {};
    if (this.token) headers.Authorization = "Bearer " + this.token;
    if (!(data instanceof FormData)) headers["Content-Type"] = "application/json";

    const body = data instanceof FormData ? data : JSON.stringify(data);

    return new Promise(res => {
      fetch(path, { method: "POST", body, headers })
        .then(resp => resp.json())
        .then(res)
        .catch((error) => res({ success: false, errors: [error.toString()] }));
    });
  }
}

function fixPrijava(p: Prijava) {
  p.prvoVrijemePrijave = new Date(p.prvoVrijemePrijave);
  p.vrijemeOtklona = p.vrijemeOtklona && new Date(p.vrijemeOtklona);
}

// GET REQUESTS

export async function getPrijava(id: number): Promise<Response<Prijava>> {
  const r = await Fetcher.get<Prijava>("/api/prijave/" + id);
  r.data && fixPrijava(r.data);
  return r;
}

export async function getPrijave(options?: PrijaveOptions): Promise<Response<Prijava[]>> {
  const r = await Fetcher.get<Prijava[]>("/api/prijave?" + new URLSearchParams(options));
  r.data?.forEach(fixPrijava);
  return r;
}

export async function getUserPrijave(id: string): Promise<Response<Prijava[]>> {
  const r = await Fetcher.get<Prijava[]>("/api/prijave", { id });
  r.data?.forEach(fixPrijava);
  return r;
}

export function getOstecenja(): Promise<Response<TipOstecenja[]>> {
  return Fetcher.get("/api/ostecenja");
}

export function getGradskiUredi(): Promise<Response<GradskiUred[]>> {
  return Fetcher.get("/api/uredi");
}

export function getAllUsers(): Promise<Response<User[]>> {
  return Fetcher.get("/api/korisnici");
}

// POST REQUESTS

export async function addPrijava(prijava: BarebonesPrijava, images: RcFile[]): Promise<Response<Prijava[]>> {
  const data = new FormData();

  Object.entries(prijava).forEach((entry) =>
    data.append(entry[0], typeof entry[1] === "string" ? entry[1] : JSON.stringify(entry[1]))
  );
  images.forEach((im) => data.append("slike", im, im.name));

  const r = await Fetcher.post<Prijava[]>("/api/addPrijava", data);
  /*
  if(r.data?.newReport) {
    r.data?.newReport = fixPrijava(r.data?.newReport);
  }
  r.data?.nearbyReports?.forEach(fixPrijava);

   */
  return r;

}

export function connectPrijave(idOneConnecting: number, idTo: number): Promise<Response<unknown>> {
  return Fetcher.post("/api/makeChild", { child_id: idOneConnecting, parent_id: idTo });
}

export function deleteUser(id: number): Promise<Response<unknown>> {
  return Fetcher.post("/api/makeChild", { id });
}

export function login(data: UserLogin): Promise<Response<LoginData>> {
  return Fetcher.post("/api/login", data);
}

export function register(data: UserRegiser): Promise<Response<LoginData>> {
  return Fetcher.post("/api/register", data);
}
