<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.assetLive.home.createOrEditLabel"
          data-cy="AssetLiveCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.assetLive.home.createOrEditLabel')"
        >
          Create or edit a AssetLive
        </h2>
        <div>
          <div class="form-group" v-if="assetLive.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="assetLive.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.totalAssets')" for="asset-live-totalAssets"
              >Total Assets</label
            >
            <input
              type="number"
              class="form-control"
              name="totalAssets"
              id="asset-live-totalAssets"
              data-cy="totalAssets"
              :class="{ valid: !$v.assetLive.totalAssets.$invalid, invalid: $v.assetLive.totalAssets.$invalid }"
              v-model.number="$v.assetLive.totalAssets.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.currentAssets')" for="asset-live-currentAssets"
              >Current Assets</label
            >
            <input
              type="number"
              class="form-control"
              name="currentAssets"
              id="asset-live-currentAssets"
              data-cy="currentAssets"
              :class="{ valid: !$v.assetLive.currentAssets.$invalid, invalid: $v.assetLive.currentAssets.$invalid }"
              v-model.number="$v.assetLive.currentAssets.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.moneyTotal')" for="asset-live-moneyTotal"
              >Money Total</label
            >
            <input
              type="number"
              class="form-control"
              name="moneyTotal"
              id="asset-live-moneyTotal"
              data-cy="moneyTotal"
              :class="{ valid: !$v.assetLive.moneyTotal.$invalid, invalid: $v.assetLive.moneyTotal.$invalid }"
              v-model.number="$v.assetLive.moneyTotal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.cashShop')" for="asset-live-cashShop"
              >Cash Shop</label
            >
            <input
              type="number"
              class="form-control"
              name="cashShop"
              id="asset-live-cashShop"
              data-cy="cashShop"
              :class="{ valid: !$v.assetLive.cashShop.$invalid, invalid: $v.assetLive.cashShop.$invalid }"
              v-model.number="$v.assetLive.cashShop.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.cashOwner')" for="asset-live-cashOwner"
              >Cash Owner</label
            >
            <input
              type="number"
              class="form-control"
              name="cashOwner"
              id="asset-live-cashOwner"
              data-cy="cashOwner"
              :class="{ valid: !$v.assetLive.cashOwner.$invalid, invalid: $v.assetLive.cashOwner.$invalid }"
              v-model.number="$v.assetLive.cashOwner.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.bankAccount')" for="asset-live-bankAccount"
              >Bank Account</label
            >
            <input
              type="number"
              class="form-control"
              name="bankAccount"
              id="asset-live-bankAccount"
              data-cy="bankAccount"
              :class="{ valid: !$v.assetLive.bankAccount.$invalid, invalid: $v.assetLive.bankAccount.$invalid }"
              v-model.number="$v.assetLive.bankAccount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.goods')" for="asset-live-goods">Goods</label>
            <input
              type="number"
              class="form-control"
              name="goods"
              id="asset-live-goods"
              data-cy="goods"
              :class="{ valid: !$v.assetLive.goods.$invalid, invalid: $v.assetLive.goods.$invalid }"
              v-model.number="$v.assetLive.goods.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.debitor')" for="asset-live-debitor">Debitor</label>
            <input
              type="number"
              class="form-control"
              name="debitor"
              id="asset-live-debitor"
              data-cy="debitor"
              :class="{ valid: !$v.assetLive.debitor.$invalid, invalid: $v.assetLive.debitor.$invalid }"
              v-model.number="$v.assetLive.debitor.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.longTermAssets')" for="asset-live-longTermAssets"
              >Long Term Assets</label
            >
            <input
              type="number"
              class="form-control"
              name="longTermAssets"
              id="asset-live-longTermAssets"
              data-cy="longTermAssets"
              :class="{ valid: !$v.assetLive.longTermAssets.$invalid, invalid: $v.assetLive.longTermAssets.$invalid }"
              v-model.number="$v.assetLive.longTermAssets.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.transport')" for="asset-live-transport"
              >Transport</label
            >
            <input
              type="number"
              class="form-control"
              name="transport"
              id="asset-live-transport"
              data-cy="transport"
              :class="{ valid: !$v.assetLive.transport.$invalid, invalid: $v.assetLive.transport.$invalid }"
              v-model.number="$v.assetLive.transport.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.equipment')" for="asset-live-equipment"
              >Equipment</label
            >
            <input
              type="number"
              class="form-control"
              name="equipment"
              id="asset-live-equipment"
              data-cy="equipment"
              :class="{ valid: !$v.assetLive.equipment.$invalid, invalid: $v.assetLive.equipment.$invalid }"
              v-model.number="$v.assetLive.equipment.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.realEstate')" for="asset-live-realEstate"
              >Real Estate</label
            >
            <input
              type="number"
              class="form-control"
              name="realEstate"
              id="asset-live-realEstate"
              data-cy="realEstate"
              :class="{ valid: !$v.assetLive.realEstate.$invalid, invalid: $v.assetLive.realEstate.$invalid }"
              v-model.number="$v.assetLive.realEstate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.other')" for="asset-live-other">Other</label>
            <input
              type="number"
              class="form-control"
              name="other"
              id="asset-live-other"
              data-cy="other"
              :class="{ valid: !$v.assetLive.other.$invalid, invalid: $v.assetLive.other.$invalid }"
              v-model.number="$v.assetLive.other.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.code')" for="asset-live-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="asset-live-code"
              data-cy="code"
              :class="{ valid: !$v.assetLive.code.$invalid, invalid: $v.assetLive.code.$invalid }"
              v-model="$v.assetLive.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetLive.notes')" for="asset-live-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="asset-live-notes"
              data-cy="notes"
              :class="{ valid: !$v.assetLive.notes.$invalid, invalid: $v.assetLive.notes.$invalid }"
              v-model="$v.assetLive.notes.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.assetLive.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./asset-live-update.component.ts"></script>
