export interface CapacitorPhoneHintPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
