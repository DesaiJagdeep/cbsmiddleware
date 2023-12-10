export interface IFactoryMaster {
  id: number;
  factoryName?: string | null;
  factoryNameMr?: string | null;
  factoryCode?: number | null;
  factoryCodeMr?: string | null;
  factoryAddress?: string | null;
  factoryAddressMr?: string | null;
  description?: string | null;
  status?: boolean | null;
}

export type NewFactoryMaster = Omit<IFactoryMaster, 'id'> & { id: null };
