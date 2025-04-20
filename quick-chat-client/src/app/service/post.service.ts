import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private url = 'http://localhost:8080'
  constructor(private http: HttpClient) { }

  persistPost(post: any): Observable<any> {
    return this.http.post<any>(`${this.url}/post`, post)
  }

  getAllPosts(): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/post`)
  }

  getPostById(id: string): Observable<any> {
    return this.http.get<any>(`${this.url}/post/${id}`)
  }

  getPostsByUserName(userName: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/post/user/${userName}`)
  }

  getPostsByUserEmail(userEmail: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/post/email/${userEmail}`)
  }

  updatePost(id: string, post: any): Observable<any> {
    return this.http.put<any>(`${this.url}/post/${id}`, post)
  }

  updateLikeCount(postId: string, count: number, isLike: boolean): Observable<any> {
    const body = { count, isLike };
    return this.http.put<any>(`http://localhost:8080/posts/${postId}/likes`, body);
}

  deletePost(id: string): Observable<any> {
    return this.http.delete<any>(`${this.url}/post/${id}`)
  }

  getMostLikedPost(): Observable<void> {
    return this.http.get<void>(`${this.url}/post/most-liked`)
  }

  getPostsByFilter(filter: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.url}/post/filter`, filter)
  }
}
