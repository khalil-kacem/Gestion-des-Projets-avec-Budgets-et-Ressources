import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface Toast {
  id: number;
  message: string;
  type: 'success' | 'error' | 'info';
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  private toasts: Toast[] = [];
  private toastSubject = new BehaviorSubject<Toast[]>([]);
  private idCounter = 0;

  getToasts(): Observable<Toast[]> {
    return this.toastSubject.asObservable();
  }

  success(message: string): void {
    this.add(message, 'success');
  }

  error(message: string): void {
    this.add(message, 'error');
  }

  info(message: string): void {
    this.add(message, 'info');
  }

  private add(message: string, type: 'success' | 'error' | 'info'): void {
    const toast: Toast = {
      id: ++this.idCounter,
      message,
      type
    };
    this.toasts.push(toast);
    this.toastSubject.next([...this.toasts]);

    setTimeout(() => {
      this.remove(toast.id);
    }, 3000);
  }

  remove(id: number): void {
    this.toasts = this.toasts.filter(t => t.id !== id);
    this.toastSubject.next([...this.toasts]);
  }
}