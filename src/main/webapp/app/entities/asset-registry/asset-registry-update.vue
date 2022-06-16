<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.assetRegistry.home.createOrEditLabel"
          data-cy="AssetRegistryCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.assetRegistry.home.createOrEditLabel')"
        >
          Create or edit a AssetRegistry
        </h2>
        <div>
          <div class="form-group" v-if="assetRegistry.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="assetRegistry.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.today')" for="asset-registry-today">Today</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="asset-registry-today"
                  v-model="$v.assetRegistry.today.$model"
                  name="today"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="asset-registry-today"
                data-cy="today"
                type="text"
                class="form-control"
                name="today"
                :class="{ valid: !$v.assetRegistry.today.$invalid, invalid: $v.assetRegistry.today.$invalid }"
                v-model="$v.assetRegistry.today.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.assetRegistry.today.$anyDirty && $v.assetRegistry.today.$invalid">
              <small class="form-text text-danger" v-if="!$v.assetRegistry.today.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.totalAssets')" for="asset-registry-totalAssets"
              >Total Assets</label
            >
            <input
              type="number"
              class="form-control"
              name="totalAssets"
              id="asset-registry-totalAssets"
              data-cy="totalAssets"
              :class="{ valid: !$v.assetRegistry.totalAssets.$invalid, invalid: $v.assetRegistry.totalAssets.$invalid }"
              v-model.number="$v.assetRegistry.totalAssets.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.assetRegistry.currentAssets')"
              for="asset-registry-currentAssets"
              >Current Assets</label
            >
            <input
              type="number"
              class="form-control"
              name="currentAssets"
              id="asset-registry-currentAssets"
              data-cy="currentAssets"
              :class="{ valid: !$v.assetRegistry.currentAssets.$invalid, invalid: $v.assetRegistry.currentAssets.$invalid }"
              v-model.number="$v.assetRegistry.currentAssets.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.moneyTotal')" for="asset-registry-moneyTotal"
              >Money Total</label
            >
            <input
              type="number"
              class="form-control"
              name="moneyTotal"
              id="asset-registry-moneyTotal"
              data-cy="moneyTotal"
              :class="{ valid: !$v.assetRegistry.moneyTotal.$invalid, invalid: $v.assetRegistry.moneyTotal.$invalid }"
              v-model.number="$v.assetRegistry.moneyTotal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.cashShop')" for="asset-registry-cashShop"
              >Cash Shop</label
            >
            <input
              type="number"
              class="form-control"
              name="cashShop"
              id="asset-registry-cashShop"
              data-cy="cashShop"
              :class="{ valid: !$v.assetRegistry.cashShop.$invalid, invalid: $v.assetRegistry.cashShop.$invalid }"
              v-model.number="$v.assetRegistry.cashShop.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.cashOwner')" for="asset-registry-cashOwner"
              >Cash Owner</label
            >
            <input
              type="number"
              class="form-control"
              name="cashOwner"
              id="asset-registry-cashOwner"
              data-cy="cashOwner"
              :class="{ valid: !$v.assetRegistry.cashOwner.$invalid, invalid: $v.assetRegistry.cashOwner.$invalid }"
              v-model.number="$v.assetRegistry.cashOwner.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.bankAccount')" for="asset-registry-bankAccount"
              >Bank Account</label
            >
            <input
              type="number"
              class="form-control"
              name="bankAccount"
              id="asset-registry-bankAccount"
              data-cy="bankAccount"
              :class="{ valid: !$v.assetRegistry.bankAccount.$invalid, invalid: $v.assetRegistry.bankAccount.$invalid }"
              v-model.number="$v.assetRegistry.bankAccount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.goods')" for="asset-registry-goods">Goods</label>
            <input
              type="number"
              class="form-control"
              name="goods"
              id="asset-registry-goods"
              data-cy="goods"
              :class="{ valid: !$v.assetRegistry.goods.$invalid, invalid: $v.assetRegistry.goods.$invalid }"
              v-model.number="$v.assetRegistry.goods.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.debitor')" for="asset-registry-debitor"
              >Debitor</label
            >
            <input
              type="number"
              class="form-control"
              name="debitor"
              id="asset-registry-debitor"
              data-cy="debitor"
              :class="{ valid: !$v.assetRegistry.debitor.$invalid, invalid: $v.assetRegistry.debitor.$invalid }"
              v-model.number="$v.assetRegistry.debitor.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.assetRegistry.longTermAssets')"
              for="asset-registry-longTermAssets"
              >Long Term Assets</label
            >
            <input
              type="number"
              class="form-control"
              name="longTermAssets"
              id="asset-registry-longTermAssets"
              data-cy="longTermAssets"
              :class="{ valid: !$v.assetRegistry.longTermAssets.$invalid, invalid: $v.assetRegistry.longTermAssets.$invalid }"
              v-model.number="$v.assetRegistry.longTermAssets.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.transport')" for="asset-registry-transport"
              >Transport</label
            >
            <input
              type="number"
              class="form-control"
              name="transport"
              id="asset-registry-transport"
              data-cy="transport"
              :class="{ valid: !$v.assetRegistry.transport.$invalid, invalid: $v.assetRegistry.transport.$invalid }"
              v-model.number="$v.assetRegistry.transport.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.equipment')" for="asset-registry-equipment"
              >Equipment</label
            >
            <input
              type="number"
              class="form-control"
              name="equipment"
              id="asset-registry-equipment"
              data-cy="equipment"
              :class="{ valid: !$v.assetRegistry.equipment.$invalid, invalid: $v.assetRegistry.equipment.$invalid }"
              v-model.number="$v.assetRegistry.equipment.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.realEstate')" for="asset-registry-realEstate"
              >Real Estate</label
            >
            <input
              type="number"
              class="form-control"
              name="realEstate"
              id="asset-registry-realEstate"
              data-cy="realEstate"
              :class="{ valid: !$v.assetRegistry.realEstate.$invalid, invalid: $v.assetRegistry.realEstate.$invalid }"
              v-model.number="$v.assetRegistry.realEstate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.other')" for="asset-registry-other">Other</label>
            <input
              type="number"
              class="form-control"
              name="other"
              id="asset-registry-other"
              data-cy="other"
              :class="{ valid: !$v.assetRegistry.other.$invalid, invalid: $v.assetRegistry.other.$invalid }"
              v-model.number="$v.assetRegistry.other.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.code')" for="asset-registry-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="asset-registry-code"
              data-cy="code"
              :class="{ valid: !$v.assetRegistry.code.$invalid, invalid: $v.assetRegistry.code.$invalid }"
              v-model="$v.assetRegistry.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.assetRegistry.notes')" for="asset-registry-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="asset-registry-notes"
              data-cy="notes"
              :class="{ valid: !$v.assetRegistry.notes.$invalid, invalid: $v.assetRegistry.notes.$invalid }"
              v-model="$v.assetRegistry.notes.$model"
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
            :disabled="$v.assetRegistry.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./asset-registry-update.component.ts"></script>
