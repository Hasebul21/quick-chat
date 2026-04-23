import { environment } from '../environments/environment';

export function apiBaseUrl(): string {
  return environment.apiBaseUrl;
}

export function sockJsUrl(): string {
  return `${environment.apiBaseUrl}/ws`;
}

/**
 * STOMP connect headers: backend maps {@code userId} to the session principal for user destinations.
 */
export function stompConnectHeaders(userId: string | number | null | undefined): Record<string, string> {
  if (userId == null || userId === '') {
    return {};
  }
  return { userId: String(userId) };
}
