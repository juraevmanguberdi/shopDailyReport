export interface IRequiredProductType {
  id?: number;
  name?: string;
  code?: string | null;
  notes?: string | null;
}

export class RequiredProductType implements IRequiredProductType {
  constructor(public id?: number, public name?: string, public code?: string | null, public notes?: string | null) {}
}
