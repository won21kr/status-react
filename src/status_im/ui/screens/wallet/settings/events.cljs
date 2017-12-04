(ns status-im.ui.screens.wallet.settings.events
  (:require [status-im.utils.ethereum.core :as ethereum]
            [status-im.utils.handlers :as handlers]))

(defn- mark-checked [ids id checked?]
  (if checked?
    (conj ids id)
    (disj ids id)))

(handlers/register-handler-fx
  :wallet.settings/toggle-visible-token
  (fn [{{:keys [network] :as db} :db} [_ symbol checked?]]
    (let [chain-id (ethereum/network->chain-id network)
          path     [:wallet :settings :visible-tokens chain-id]
          udb      (update-in db path #(mark-checked % symbol checked?))]
      {:db       udb
       #_:dispatch #_[:account-update-visible-tokens (get-in db path)]})))