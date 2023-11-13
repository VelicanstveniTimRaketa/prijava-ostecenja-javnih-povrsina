import { Location } from "./types";

export function googleToLocation(latlng: google.maps.LatLng): Location {
  return { latitude: latlng.lat(), longitude: latlng.lng() };
}

export function locationToGoogle(latlng: Location): google.maps.LatLngLiteral {
  return { lat: latlng.latitude, lng: latlng.longitude };
}
