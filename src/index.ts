import { registerPlugin } from '@capacitor/core';

import type { CapacitorPhoneHintPlugin } from './definitions';

const CapacitorPhoneHint = registerPlugin<CapacitorPhoneHintPlugin>('CapacitorPhoneHint', {
  web: () => import('./web').then((m) => new m.CapacitorPhoneHintWeb()),
});

export * from './definitions';
export { CapacitorPhoneHint };
