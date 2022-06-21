import { IRequiredProductType } from '@/shared/model/required-product-type.model';

export interface IRequiredProduct {
  id?: number;
  name?: string;
  note?: string | null;
  code?: string | null;
  requiredProductType?: IRequiredProductType;
}

export class RequiredProduct implements IRequiredProduct {
  constructor(
    public id?: number,
    public name?: string,
    public note?: string | null,
    public code?: string | null,
    public requiredProductType?: IRequiredProductType
  ) {}
}
