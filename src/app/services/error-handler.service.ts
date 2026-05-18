import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { ApiError } from '../models/error.model';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred';
    let errorDetails: ApiError | null = null;

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorDetails = error.error as ApiError;
      if (errorDetails.message) {
        errorMessage = errorDetails.message;
      } else if (error.status === 400) {
        errorMessage = 'Bad Request - Invalid data';
      } else if (error.status === 404) {
        errorMessage = 'Resource not found';
      } else if (error.status === 500) {
        errorMessage = 'Internal Server Error';
      }
    }

    console.error('Error details:', errorDetails);
    return throwError(() => ({ message: errorMessage, details: errorDetails }));
  }

  getValidationErrors(error: ApiError): string[] {
    if (error.errors) {
      return Object.values(error.errors);
    }
    return [error.message];
  }
}