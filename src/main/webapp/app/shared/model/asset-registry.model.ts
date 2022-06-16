export interface IAssetRegistry {
  id?: number;
  today?: Date;
  totalAssets?: number | null;
  currentAssets?: number | null;
  moneyTotal?: number | null;
  cashShop?: number | null;
  cashOwner?: number | null;
  bankAccount?: number | null;
  goods?: number | null;
  debitor?: number | null;
  longTermAssets?: number | null;
  transport?: number | null;
  equipment?: number | null;
  realEstate?: number | null;
  other?: number | null;
  code?: string | null;
  notes?: string | null;
}

export class AssetRegistry implements IAssetRegistry {
  constructor(
    public id?: number,
    public today?: Date,
    public totalAssets?: number | null,
    public currentAssets?: number | null,
    public moneyTotal?: number | null,
    public cashShop?: number | null,
    public cashOwner?: number | null,
    public bankAccount?: number | null,
    public goods?: number | null,
    public debitor?: number | null,
    public longTermAssets?: number | null,
    public transport?: number | null,
    public equipment?: number | null,
    public realEstate?: number | null,
    public other?: number | null,
    public code?: string | null,
    public notes?: string | null
  ) {}
}
