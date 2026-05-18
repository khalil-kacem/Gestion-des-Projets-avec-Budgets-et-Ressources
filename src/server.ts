import { AngularAppEngine, createRequestHandler } from '@angular/ssr';

const angularAppEngine = new AngularAppEngine();

export function app() {
  return createRequestHandler(async (req) => {
    return angularAppEngine.handle(req, {
      bootstrap: () => import('./main.server'),
    });
  });
}

export default app;